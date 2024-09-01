/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.appointmentBean;

import com.dreamersoft.hms.entity.AppointmentEntity;
import com.dreamersoft.hms.entity.AppointmentStatusEntity;
import com.dreamersoft.hms.entity.AppointmentTypeEntity;
import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.PatientEntity;
import com.dreamersoft.hms.sessionbeans.appointmentStatusBean.AppointmentStatusManagerRemote;
import com.dreamersoft.hms.sessionbeans.appointmentStatusBean.AppointmentStatusNotFoundException;
import com.dreamersoft.hms.sessionbeans.appointmentTypeBean.AppointmentTypeManagerRemote;
import com.dreamersoft.hms.sessionbeans.appointmentTypeBean.AppointmentTypeNotFoundException;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorManagerRemote;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorNotFoundException;
import com.dreamersoft.hms.sessionbeans.patientBean.PatientManagerRemote;
import com.dreamersoft.hms.sessionbeans.patientBean.PatientNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author aizaz
 */
@Stateless
public class AppointmentManager implements AppointmentManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    PatientManagerRemote patientManager;

    @EJB
    DoctorManagerRemote doctorManager;

    @EJB
    AppointmentStatusManagerRemote appointmentStatusManager;

    @EJB
    AppointmentTypeManagerRemote appointmentTypeManager;

    @Override
    public AppointmentEntity getAppointmentByID(int appointmentID) throws AppointmentNotFoundException {
        AppointmentEntity appointmentEntity;
        Query qry = em.createQuery("Select s From AppointmentEntity s Where s.appointmentId=:appointmentID  AND s.isDeleted=false");
        qry.setParameter("appointmentID", appointmentID);
        try {
            appointmentEntity = (AppointmentEntity) qry.getSingleResult();

        } catch (NoResultException ex) {
            throw new AppointmentNotFoundException("Appointment Not found with this appointment ID : " + appointmentID);
        }
        return appointmentEntity;

    }

    @Override
    public AppointmentEntity getAppointmentByAppointmentDateAndPatientIDandDoctorID(Date date, int patientID, int doctorID) throws AppointmentNotFoundException, InvalidAppointmentDateTimeException, PatientNotFoundException, DoctorNotFoundException {

        AppointmentEntity appointmentEntity;

        PatientEntity patientEntity = patientManager.getPatientByID(patientID);

        DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorID);

        Query qry = em.createQuery("SELECT s FROM AppointmentEntity s Where s.patientId.patientId=:patientID AND s.doctorId.doctorId=:doctorID AND DATE(s.appointmentTime)=:date and s.isDeleted=false");
        qry.setParameter("patientID", patientID);
        qry.setParameter("doctorID", doctorID);
        qry.setParameter("date", date);
        try {
            appointmentEntity = (AppointmentEntity) qry.getSingleResult();
        } catch (NoResultException ex) {
            throw new AppointmentNotFoundException("Appointment Not found With the provided values : " + "\n PatientID : " + patientID + " \n doctorID : " + doctorID + " \n  Date: " + date);

        }
        return appointmentEntity;

    }

    @Override
    public List<AppointmentEntity> getAppointmentListByPatientIDandDoctorID(int patientID, int doctorID) throws AppointmentNotFoundException, PatientNotFoundException, DoctorNotFoundException, DoctorNotFoundException, PatientNotFoundException {
        List<AppointmentEntity> appointmentEntityList;
        PatientEntity patientEntity = patientManager.getPatientByID(patientID);

        DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorID);

        Query qry = em.createQuery("SELECT s FROM AppointmentEntity s WHERE s.patientId.patientId=:patientID AND s.doctorId.doctorId=:doctorID AND s.isDeleted=false");
        qry.setParameter("patientID", patientID);
        qry.setParameter("doctorID", doctorID);
        appointmentEntityList = (List<AppointmentEntity>) qry.getResultList();
        if (appointmentEntityList.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment Not Found with these PatientID: " + patientID + " and doctor ID : " + doctorID);
        }
        return appointmentEntityList;
    }

    @Override
    public List<AppointmentEntity> getAppointmentListByDoctorIDandDate(int doctorID, Date date) throws AppointmentNotFoundException, InvalidAppointmentDateTimeException, DoctorNotFoundException {
        List<AppointmentEntity> appointmentEntityList;
        DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorID);
        Query qry = em.createQuery("SELECT s FROM AppointmentEntity s WHERE s.doctorId.doctorId=:doctorID AND DATE(s.appointmentTime)=:date  AND  s.isDeleted=false");
        qry.setParameter("doctorID", doctorID);
        qry.setParameter("date", date);
        appointmentEntityList = (List<AppointmentEntity>) qry.getResultList();
        if (appointmentEntityList.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment Not Found With this doctor ID : " + doctorID + " \n Date: " + date);
        }
        return appointmentEntityList;

    }

    @Override
    public List<AppointmentEntity> getAppointmentListByDoctorIDandAppointmentStatusID(int doctorID, int appointmentStatusID) throws AppointmentNotFoundException, AppointmentStatusNotFoundException, DoctorNotFoundException {
        AppointmentStatusEntity appointmentStatusEntity = appointmentStatusManager.getAppointmentStatusByID(appointmentStatusID);
        DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorID);
        List<AppointmentEntity> appointmentEntityList;
        Query qry = em.createQuery("SELECT s FROM AppointmentEntity s WHERE s.doctorId.doctorId=:doctorID AND s.appointmentStatusId.appointmentStatusId=:appointmentStatusID AND s.isDeleted=false");
        qry.setParameter("doctorID", doctorID);
        qry.setParameter("appointmentStatusID", appointmentStatusID);
        appointmentEntityList = (List<AppointmentEntity>) qry.getResultList();
        if (appointmentEntityList.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment Not Found With this Doctor ID: " + doctorID + " /n and AppointmentStatusID: " + appointmentStatusID);
        }

        return appointmentEntityList;
    }

    @Override
    public List<AppointmentEntity> getAllAppointmentList() throws AppointmentNotFoundException {
        List<AppointmentEntity> appointmentEntityList;
        Query qry = em.createQuery("SELECT s FROM AppointmentEntity s Where s.isDeleted=false");

        appointmentEntityList = (List<AppointmentEntity>) qry.getResultList();
        if (appointmentEntityList.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment Not Found ");
        }
        return appointmentEntityList;

    }

    @Override
    public AppointmentEntity deleteAppointmentByID(int appointmentID) throws AppointmentNotFoundException {
        AppointmentEntity appointmentEntity = new AppointmentEntity();

        try {
            appointmentEntity = this.getAppointmentByID(appointmentID);
        } catch (AppointmentNotFoundException e) {
            Logger.getLogger(AppointmentManager.class.getName()).log(Level.SEVERE, null, e);
        }

        appointmentEntity.setIsDeleted(true);
        em.persist(appointmentEntity);
        em.flush();
        em.refresh(appointmentEntity);
        return appointmentEntity;
    }

    @Override
    public AppointmentEntity addNewAppointment(int patientID, int doctorID, int appointmentTypeID, Date appointmentDate) throws AppointmentAlreadyExistsException, InvalidAppointmentDateTimeException, PatientNotFoundException, DoctorNotFoundException, AppointmentTypeNotFoundException, AppointmentStatusNotFoundException {

       
        int nextTokenNumber = this.getNextTokenNumber(doctorID, appointmentDate);

//                        change the appointment status value to someting meaningful in future
        AppointmentStatusEntity appointmentStatusEntity = appointmentStatusManager.getAppointmentStatusByID(1);

        PatientEntity patientEntity = patientManager.getPatientByID(patientID);

        DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorID);

        AppointmentTypeEntity appointmentTypeEntity = appointmentTypeManager.getAppointmentTypeByID(appointmentTypeID);

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        try {
            this.getAppointmentByAppointmentDateAndPatientIDandDoctorID(appointmentDate, patientID, doctorID);
            throw new AppointmentAlreadyExistsException("Appointment Already exist for the same doctor");
        } catch (AppointmentNotFoundException | PatientNotFoundException | DoctorNotFoundException ex) {

            appointmentEntity.setAppointmentTime(appointmentDate);
            appointmentEntity.setAppointmentTypeId(appointmentTypeEntity);
            appointmentEntity.setDoctorId(doctorEntity);
            appointmentEntity.setPatientId(patientEntity);
            appointmentEntity.setAppointmentStatusId(appointmentStatusEntity);
            appointmentEntity.setTokenNumber(nextTokenNumber);
            em.persist(appointmentEntity);
            em.flush();

            em.refresh(appointmentEntity);
        }

        return appointmentEntity;
    }

    public int getNextTokenNumber(int doctorId, Date date) {
        int tokenNumber = 1;
        Query qry = em.createQuery("Select max(a.tokenNumber) from AppointmentEntity a WHERE a.doctorId.doctorId=:doctorId and cast(a.appointmentTime as LocalDate) =:date");
        qry.setParameter("doctorId", doctorId);
        qry.setParameter("date", date);

        if (qry.getSingleResult() == null) {
            tokenNumber = 1;
        } else {
            tokenNumber = (Integer) qry.getSingleResult() + 1;
        }
        return tokenNumber;
    }

    @Override
    public AppointmentEntity updateAppointmentByID(int appointmentID, int newPatientID, int newDoctorID, int newAppointmentStatusID, Date newAppointmentDate, int newAppointmentTypeID) throws DuplicateAppointmentException, InvalidAppointmentDateTimeException, AppointmentNotFoundException, PatientNotFoundException, DoctorNotFoundException, AppointmentStatusNotFoundException, AppointmentTypeNotFoundException {
    
        AppointmentEntity appointmentEntity=this.getAppointmentByID(appointmentID);
        try{
        AppointmentEntity checkAppointmentEntity=this.getAppointmentByAppointmentDateAndPatientIDandDoctorID(newAppointmentDate, newPatientID, newDoctorID);
           if(!Objects.equals(appointmentEntity.getAppointmentId(), checkAppointmentEntity.getAppointmentId())){           
           throw new DuplicateAppointmentException("Appointment already exists against another record  By PatientID: "+newPatientID+" and DocotorID : "+newDoctorID+" and AppointmentDate: "+newAppointmentDate);
           
           }
        }catch (NotFoundException ex){
             AppointmentStatusEntity appointmentStatusEntity = appointmentStatusManager.getAppointmentStatusByID(newAppointmentStatusID);

        PatientEntity patientEntity = patientManager.getPatientByID(newPatientID);

        DoctorEntity doctorEntity = doctorManager.getDoctorByID(newDoctorID);

        AppointmentTypeEntity appointmentTypeEntity = appointmentTypeManager.getAppointmentTypeByID(newAppointmentTypeID);

            
            int nextTokenNumber=this.getNextTokenNumber(newDoctorID, newAppointmentDate);
            
            
          appointmentEntity.setAppointmentTime(newAppointmentDate);
            appointmentEntity.setAppointmentTypeId(appointmentTypeEntity);
            appointmentEntity.setDoctorId(doctorEntity);
            appointmentEntity.setPatientId(patientEntity);
            appointmentEntity.setAppointmentStatusId(appointmentStatusEntity);            
            appointmentEntity.setTokenNumber(nextTokenNumber);
            em.persist(appointmentEntity);
            em.flush();

            em.refresh(appointmentEntity);
        }
          
        return appointmentEntity;
    
    
    
    
    }
    
    
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        em.persist(object);
    }
}
