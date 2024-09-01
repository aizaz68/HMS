/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.HospitalEntity;
import com.dreamersoft.hms.entity.UsersEntity;
import com.dreamersoft.hms.sessionbeans.hospitalBean.HospitalManagerRemote;
import com.dreamersoft.hms.sessionbeans.hospitalBean.HospitalNotFoundException;
import com.dreamersoft.hms.sessionbeans.userBean.UserManagerRemote;
import com.dreamersoft.hms.sessionbeans.userBean.UserNotFoundException;
import java.util.List;
import java.util.Objects;
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
public class DoctorManager implements DoctorManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    UserManagerRemote userManager;

    @EJB
    HospitalManagerRemote hospitalManager;

    @Override
    public DoctorEntity getDoctorByID(int doctorID) throws DoctorNotFoundException {
        DoctorEntity doctorEntity;
        Query qry = em.createQuery("select d from DoctorEntity d where d.doctorId=:doctorID and d.isDeleted = false");
        qry.setParameter("doctorID", doctorID);
        try {
            doctorEntity = (DoctorEntity) qry.getSingleResult();
        } catch (NoResultException ex) {
            throw new DoctorNotFoundException("Doctor Not Found For This Doctor id:" + doctorID);
        }

        return doctorEntity;
    }

    @Override
    public boolean validateCNIC(String doctorCNIC) {
        return !(!doctorCNIC.isEmpty() && doctorCNIC.length() >= 13);

    }

    @Override
    public List<DoctorEntity> getDoctorListByCNIC(String doctorCNIC) throws DoctorNotFoundException, InvalidDoctorCNICException {
        this.validateCNIC(doctorCNIC);
        Query qry = em.createQuery("select d from DoctorEntity d where d.doctorCnic=:doctorCNIC and d.isDeleted = false");
        qry.setParameter("doctorCNIC", doctorCNIC);
        List<DoctorEntity> doctorEntityList;
        doctorEntityList = qry.getResultList();
        if (doctorEntityList.isEmpty()) {
            throw new DoctorNotFoundException("Doctor with CNIC " + doctorCNIC + " not found");
        }

        return doctorEntityList;
    }

    @Override
    public boolean cnicExist(String doctorCNIC) {

        Query qry = em.createQuery("SELECT COUNT(d) FROM doctorEntity d WHERE d.doctorCNIC=: doctorCNIC AND d.isDeleted = false");
        qry.setParameter("doctorCNIC", doctorCNIC);
        return (long) qry.getSingleResult() > 0;
    }

    @Override
    public DoctorEntity getDoctorByUserIDandDoctorCNICandHospitalID(int userID, String doctorCNIC, int hospitalID) throws DoctorNotFoundException, UserNotFoundException, HospitalNotFoundException, InvalidDoctorCNICException {
        UsersEntity userEntity = userManager.getUserByID(userID);
        if (userEntity == null) {
            throw new UserNotFoundException("User Not Found For this" + userID);
        }
        HospitalEntity hospitalEntity = hospitalManager.getHospitalByID(hospitalID);
        if (hospitalEntity == null) {
            throw new HospitalNotFoundException("Hospital Not Found For this" + hospitalID);
        }
        this.validateCNIC(doctorCNIC);
        Query qry = em.createQuery("select a from DoctorEntity a where a.doctorCnic=:doctorCNIC and a.userId.userId=:userID and a.hospitalId.hospitalId=:hospitalID and a.isDeleted= false");
        qry.setParameter("userID", userID);
        qry.setParameter("doctorCNIC", doctorCNIC);
        qry.setParameter("hospitalID", hospitalID);
        DoctorEntity doctorEntity;
        try {
            doctorEntity = (DoctorEntity) qry.getSingleResult();

        } catch (NoResultException ex) {
            throw new DoctorNotFoundException("Doctor not found for these values " + "UserId:" + userID + " DoctorCNIC:" + doctorCNIC + " HospitalID:" + hospitalID);

        }
        return doctorEntity;
    }

    @Override
    public DoctorEntity addNewDoctor(int userID, String doctorCNIC, int hospitalID) throws DoctorNotFoundException, UserNotFoundException, HospitalNotFoundException, InvalidDoctorCNICException, DoctorAlreayExistException, DuplicateCNICException {
        DoctorEntity doctorEntity;
      
        if (!this.cnicExist(doctorCNIC)) {
            throw new DuplicateCNICException("This CNIC is already registered against another Doctor " + doctorCNIC);

        }
        try {
            doctorEntity = this.getDoctorByUserIDandDoctorCNICandHospitalID(userID, doctorCNIC, hospitalID);
            throw new DoctorAlreayExistException("Doctor already exist for these values " + "UserId:" + userID + " DoctorCNIC:" + doctorCNIC + " HospitalID:" + hospitalID);
        } catch (DoctorNotFoundException ex) {
            UsersEntity userEntity = userManager.getUserByID(userID);
            HospitalEntity hospitalEntity = hospitalManager.getHospitalByID(hospitalID);

            doctorEntity = new DoctorEntity();
            doctorEntity.setUserId(userEntity);
            doctorEntity.setDoctorCnic(doctorCNIC);
            doctorEntity.setHospitalId(hospitalEntity);

            em.persist(doctorEntity);

        }
        return doctorEntity;

    }

    @Override
    public void deleteDoctorById(int doctorID) throws DoctorNotFoundException {
        DoctorEntity doctorEntity = this.getDoctorByID(doctorID);
        doctorEntity.setIsDeleted(true);
        em.persist(doctorEntity);
    }

    @Override
    public DoctorEntity updateDoctor(int doctorID, String doctorCNIC, int hospitalID, int userID) throws DoctorNotFoundException, InvalidDoctorCNICException, UserNotFoundException, HospitalNotFoundException, DuplicateDoctorException, DuplicateCNICException {
        DoctorEntity doctorEntityUpdate = this.getDoctorByID(doctorID);
      
        if (!this.cnicExist(doctorCNIC )) {
            throw new DuplicateCNICException("This CNIC is already registered against another Doctor " + doctorCNIC);

        }
        UsersEntity userEntity = userManager.getUserByID(userID);
        HospitalEntity hospitalEntity = hospitalManager.getHospitalByID(hospitalID);
        DoctorEntity doctorEntity;
        try {
            doctorEntity = this.getDoctorByUserIDandDoctorCNICandHospitalID(userID, doctorCNIC, hospitalID);
            if (!Objects.equals(doctorEntityUpdate.getDoctorId(), doctorEntity.getDoctorId())) {
                throw new DuplicateDoctorException("Doctor already exist against another record for these values " + "UserId:" + userID + " DoctorCNIC:" + doctorCNIC + " HospitalID:" + hospitalID);

            }

        } catch (DoctorNotFoundException ex) {

            doctorEntityUpdate = new DoctorEntity();
            doctorEntityUpdate.setUserId(userEntity);
            doctorEntityUpdate.setDoctorCnic(doctorCNIC);
            doctorEntityUpdate.setHospitalId(hospitalEntity);
            doctorEntityUpdate.setDoctorId(doctorID);

            em.persist(doctorEntityUpdate);

        }

        return doctorEntityUpdate;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        em.persist(object);
    }

}
