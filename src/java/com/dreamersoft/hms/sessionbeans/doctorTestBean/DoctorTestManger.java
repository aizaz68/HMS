/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorTestBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.DoctorTestEntity;
import com.dreamersoft.hms.entity.TestEntity;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorManagerRemote;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorNotFoundException;
import com.dreamersoft.hms.sessionbeans.testBean.TestManagerRemote;
import com.dreamersoft.hms.sessionbeans.testBean.TestNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;

/**
 *
 * @author aizaz
 */
@Stateless
public class DoctorTestManger implements DoctorTestMangerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    DoctorManagerRemote doctorManager;
    @EJB
    TestManagerRemote testManager;

    //get doctor test by id
    @Override
    public DoctorTestEntity getDoctorTestByID(int doctorTestID) throws DoctorTestNotFoundException {
        DoctorTestEntity doctorTest = em.find(DoctorTestEntity.class, doctorTestID);
        if (doctorTest == null || doctorTest.getIsDeleted()) {
            throw new DoctorTestNotFoundException("Doctor test not found with doctorTestId = " + doctorTestID);
        }
        return doctorTest;
    }

    //creat doctor test;
    @Override
    public DoctorTestEntity addDoctorTest(int doctorId, int testId) throws TestNotFoundException, DoctorNotFoundException, DoctorTestNotFoundException, DoctorTestAlreadyExistException {
        DoctorEntity doctor = doctorManager.getDoctorByID(doctorId);
        TestEntity test = testManager.getTestByID(testId);
        DoctorTestEntity doctorTestEntity = null;
        try {
            doctorTestEntity = getDoctorTestBYDoctorIdAndTestId(doctorId, testId);
            if (doctorTestEntity != null) {
                throw new DoctorTestAlreadyExistException("Doctor Test with doctor Id =" + doctorId + "and test id = " + testId + "already exist");
            }
        } catch (TestNotFoundException ex) {
            doctorTestEntity = new DoctorTestEntity();
            doctorTestEntity.setDoctorId(doctor);
            doctorTestEntity.setTestId(test);
            doctorTestEntity.setIsDeleted(false);
            em.persist(doctorTestEntity);
        }
        return doctorTestEntity;
    }

    //delete doctor test
    @Override
    public void deleteDoctorTest(int doctorTestId) throws DoctorTestNotFoundException {
        DoctorTestEntity doctorTest = getDoctorTestByID(doctorTestId);
        doctorTest.setIsDeleted(true);
        em.merge(doctorTest);
    }

    //restore deleted doctor
    @Override
    public void restoreDoctorTestById(int doctorTestId) throws DoctorTestNotFoundException {
        DoctorTestEntity doctorTest = getDoctorTestByID(doctorTestId);
        if (doctorTest != null) {
            doctorTest.setIsDeleted(false);
            em.merge(doctorTest);
        }
    }

    //update doctor test
    @Override
    public DoctorTestEntity updateDoctorTest(int doctorTestId, int newDoctorId, int newTestId) throws DoctorTestNotFoundException, DoctorTestAlreadyExistException, TestNotFoundException, DoctorNotFoundException {
        DoctorEntity doctorEntity = doctorManager.getDoctorByID(newDoctorId);
        TestEntity testEntity = testManager.getTestByID(newTestId);
        DoctorTestEntity doctorTestEntity = null;
        
        try{
            doctorTestEntity = getDoctorTestBYDoctorIdAndTestId(newDoctorId, newTestId);
            if(doctorTestEntity != null){
                throw new DoctorTestAlreadyExistException("Doctor Test with doctor Id =" + newDoctorId + "and test id = " + newTestId + "already exist");
            }
        }catch(TestNotFoundException ex){
            doctorTestEntity = getDoctorTestByID(doctorTestId);
            if (doctorTestEntity != null) {
                doctorTestEntity.setDoctorId(doctorEntity);
                doctorTestEntity.setTestId(testEntity);
            }
        }
        
        em.merge(doctorTestEntity);
        return doctorTestEntity;
    }

    //get all doctor test that are not deleted
    @Override
    public List<DoctorTestEntity> getAllActiveDoctorTest() throws DoctorTestNotFoundException {
        List<DoctorTestEntity> allDoctorTest;
        Query qry = em.createQuery("SELECT dt FROM DoctorTestEntity dt WHERE dt.isDeleted = false", DoctorTestEntity.class);
        allDoctorTest = qry.getResultList();
        if (allDoctorTest.isEmpty()) {
            throw new DoctorTestNotFoundException("No record  found.");
        }
        return allDoctorTest;
    }

    //get all doctors by test id
    @Override
    public List<TestEntity> getAllTestsByDoctorId(int doctorId) throws TestNotFoundException, DoctorNotFoundException {
        // Query to fetch tests based on doctorId
        DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorId);

        Query qry = em.createQuery("SELECT dt.doctorId FROM DoctorTestEntity dt where dt.doctorId.doctorId = :doctorId AND dt.isDeleted = false");
        qry.setParameter("doctorId", doctorId);

        List<TestEntity> allTestsList = qry.getResultList();

        if (allTestsList.isEmpty()) {
            throw new TestNotFoundException("Doctor Tests not found with this " + doctorId);
        }
        return allTestsList;
    }

    //get all tests by doctor id
    @Override
    public List<DoctorEntity> getAllDoctorsByTestId(int testId) throws DoctorNotFoundException, TestNotFoundException {
        // Query to fetch doctors based on testId
        TestEntity testEntity = testManager.getTestByID(testId);
        
        Query qry = em.createQuery("SELECT dt.doctorId FROM DoctorTestEntity dt WHERE dt.testId.testId = :testId AND dt.isDeleted = false");
        qry.setParameter("testId", testId);
        List<DoctorEntity> allDoctorsList = qry.getResultList();
        if (allDoctorsList.isEmpty()) {
            throw new DoctorNotFoundException("Doctor not found");
        }
        return allDoctorsList;

    }

    public DoctorTestEntity getDoctorTestBYDoctorIdAndTestId(int doctorId, int testId) throws TestNotFoundException, DoctorNotFoundException, DoctorTestNotFoundException {
        DoctorTestEntity doctorTestEntity;

        DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorId);
        TestEntity testEntity = testManager.getTestByID(testId);

        Query qry = em.createQuery("SELECT s FROM DoctorTestEntity s WHERE s.doctorId.doctorId = : doctorId AND s.testId.testId = : testId AND s.isDeleted = false");
        qry.setParameter("doctorId", doctorId);
        qry.setParameter("testId", testId);
        try {
            doctorTestEntity = (DoctorTestEntity) qry.getSingleResult();
        } catch (NoResultException ex) {
            throw new DoctorTestNotFoundException("Doctor Test not found with this doctor id =" + doctorId + " and test id = " + testId);
        }

        return doctorTestEntity;
    }
    
    public List<DoctorTestEntity> getDoctorTestList() throws DoctorTestNotFoundException{
        Query qry = em.createQuery("SELECT d FROM DoctorTestEntity d WHERE d.isDeleted = false");
        List<DoctorTestEntity> doctorTestList = qry.getResultList();
        if(doctorTestList.isEmpty()){
            throw new DoctorTestNotFoundException("No record found in doctor test List");
        }
        return doctorTestList;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
