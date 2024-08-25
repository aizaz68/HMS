/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorTestBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.DoctorTestEntity;
import com.dreamersoft.hms.entity.TestEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
    

    
    //get doctor test by id
    @Override
    public DoctorTestEntity getDoctorTestByID(int doctorTestID) throws DoctorTestNotFoundException{
        DoctorTestEntity doctorTest = em.find(DoctorTestEntity.class, doctorTestID);
        if(doctorTest == null || doctorTest.getIsDeleted()){
            throw new DoctorTestNotFoundException("Doctor test not found");
        }
        return doctorTest;
    }
    
    //creat doctor test;
    @Override
    public void addDoctorTest(DoctorEntity doctor, TestEntity test) throws  NullDoctorNameException, NullTestNameException{
        if(doctor == null){
            throw new NullDoctorNameException("Doctor is null");
        }else if(test == null){
            throw new NullTestNameException("Test is null");
        }
        DoctorTestEntity doctorTest = new DoctorTestEntity();
        doctorTest.setDoctorId(doctor);
        doctorTest.setTestId(test);
        doctorTest.setIsDeleted(false);
        
    }
    
    //delete doctor test
    @Override
    public void deleteDoctorTest(int doctorTestId) throws DoctorTestNotFoundException{
       DoctorTestEntity doctorTest = getDoctorTestByID(doctorTestId);
       doctorTest.setIsDeleted(true);
       em.merge(doctorTest);
    }
    
    //restore deleted doctor
    @Override
    public void restoreDoctorTest(int doctorTestId) throws DoctorTestNotFoundException {
    DoctorTestEntity doctorTest = getDoctorTestByID(doctorTestId);
    if (doctorTest != null) {
        doctorTest.setIsDeleted(false);
        em.merge(doctorTest);
    }
}

    
    //update doctor test
    @Override
    public void updateDoctorTest(int doctorTestId,DoctorEntity newDoctor, TestEntity newTest) throws DoctorTestNotFoundException{
        DoctorTestEntity doctorTest = getDoctorTestByID(doctorTestId);
        if(doctorTest != null){
            doctorTest.setDoctorId(newDoctor);
            doctorTest.setTestId(newTest);
        }
        em.merge(doctorTest);
    }
    
    //get all doctor test that are not deleted
    @Override
    public List<DoctorTestEntity> getAllActiveDoctorTest() throws DoctorTestNotFoundException{
        List<DoctorTestEntity> allDoctorTest;
        Query qry = em.createQuery("SELECT dt FROM DoctorTestEntity dt WHERE dt.isDeleted = false", DoctorTestEntity.class);
        allDoctorTest = qry.getResultList();
        if(allDoctorTest == null){
            throw new DoctorTestNotFoundException("No record  found");
        }
        return allDoctorTest;
    }
  

    public void persist(Object object) {
        em.persist(object);
    }
}
