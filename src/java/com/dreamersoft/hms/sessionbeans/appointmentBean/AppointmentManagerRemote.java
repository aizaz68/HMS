/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.appointmentBean;

import com.dreamersoft.hms.entity.AppointmentEntity;
import com.dreamersoft.hms.sessionbeans.appointmentStatusBean.AppointmentStatusNotFoundException;
import com.dreamersoft.hms.sessionbeans.appointmentTypeBean.AppointmentTypeNotFoundException;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorNotFoundException;
import com.dreamersoft.hms.sessionbeans.patientBean.PatientNotFoundException;
import java.util.Date;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorNotFoundException;
import com.dreamersoft.hms.sessionbeans.patientBean.PatientNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface AppointmentManagerRemote {

    public AppointmentEntity getAppointmentByID(int appointmentID) throws AppointmentNotFoundException;

    public AppointmentEntity getAppointmentByAppointmentDateAndPatientIDandDoctorID(Date date, int patientID, int doctorID) throws AppointmentNotFoundException, InvalidAppointmentDateTimeException, PatientNotFoundException, DoctorNotFoundException;

    public List<AppointmentEntity> getAppointmentListByPatientIDandDoctorID(int patientID, int doctorID) throws AppointmentNotFoundException, PatientNotFoundException, DoctorNotFoundException, DoctorNotFoundException, PatientNotFoundException;

    public List<AppointmentEntity> getAppointmentListByDoctorIDandDate(int doctorID, Date date) throws AppointmentNotFoundException, InvalidAppointmentDateTimeException, DoctorNotFoundException;

    public List<AppointmentEntity> getAppointmentListByDoctorIDandAppointmentStatusID(int doctorID, int appointmentStatusID) throws AppointmentNotFoundException, AppointmentStatusNotFoundException, DoctorNotFoundException;

    public List<AppointmentEntity> getAllAppointmentList() throws AppointmentNotFoundException;

    public AppointmentEntity deleteAppointmentByID(int appointmentID) throws AppointmentNotFoundException;

    public AppointmentEntity addNewAppointment(int patientID, int doctorID, int appointmentTypeID, Date appointmentDate) throws AppointmentAlreadyExistsException, InvalidAppointmentDateTimeException, PatientNotFoundException, DoctorNotFoundException, AppointmentTypeNotFoundException, AppointmentStatusNotFoundException;

    public AppointmentEntity updateAppointmentByID(int appointmentID, int newPatientID, int newDoctorID, int newAppointmentStatusID, Date newAppointmentDate, int newAppointmentTypeID) throws DuplicateAppointmentException, InvalidAppointmentDateTimeException, AppointmentNotFoundException, PatientNotFoundException, DoctorNotFoundException, AppointmentStatusNotFoundException, AppointmentTypeNotFoundException;

    public AppointmentEntity getCurrentTokenNumberByDoctorID(int doctorID);
    
    public AppointmentEntity getPreviousTokenNumberByDoctorID(int doctorID);
    
    public AppointmentEntity getNextTokenNumberByDoctorID(int doctorID);
    
    public List<AppointmentEntity>  getAllAppointmenByDoctorIDandDateRange(int doctorID,Date dateFrom,Date dateTo);
    
    public List<AppointmentEntity> getAllAppointmentListByDoctorIDandAppointmentStatusIDandDateRange(int doctorId,int appointmentStatusID,Date dateFrom,Date dateTo);
    
    public List<AppointmentEntity> getAllAppointmentListByDoctorIDandAppointmentTypeIDandDateRange(int doctorID,int appointmentTypeID,Date dateFrom,Date dateTo);
    
    public List<AppointmentEntity> getAllAppointmentsByDoctorIDandDateRange(int doctorId,Date dateFrom,Date dateTo);
    
    
    
    
    
   
    
 
}
