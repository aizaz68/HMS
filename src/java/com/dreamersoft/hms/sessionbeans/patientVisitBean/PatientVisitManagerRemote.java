/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.patientVisitBean;

import com.dreamersoft.hms.entity.PatientVisitEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface PatientVisitManagerRemote {

    public PatientVisitEntity getPatientVisitByID(int patientVisitID) throws PatientVisitNotFoundException;

    public void addPatientVisit(Date patientVisitDate,
            String patientBloodPressure,
            Integer patientPulse,
            Float patientTemperature,
            Float patientWeight,
            Integer patientAge,
            String diagnosis1,
            String diagnosis2,
            String diagnosis3,
            String patientVisitInstructions,
            Integer patientId,
            Integer doctorId) throws PatientNotFoundException, DoctorNotFoundException;

    public List<PatientVisitEntity> findAllPatientVisitsByPatientIDAndDoctorID(int patientID, int doctorID) throws PatientVisitNotFoundException, PatientNotFoundException, DoctorNotFoundException;

    public List<PatientVisitEntity> findAllPatientVisitsByPatientID(int patientID) throws PatientVisitNotFoundException, PatientNotFoundException;

    public List<PatientVisitEntity> findAllPatientVisitsByDoctorID(int doctorID) throws PatientVisitNotFoundException, DoctorNotFoundException;

    public PatientVisitEntity findLatestVisitByPatientId(int patientId) throws PatientNotFoundException, PatientVisitNotFoundException;

    public List<PatientVisitEntity> findVisitsByDateRange(Date startDate, Date endDate) throws PatientVisitNotFoundException;

    public List<PatientVisitEntity> getAllPatientVisitList() throws PatientVisitNotFoundException;

    public void updatePatientVisit(int patientVisitId, int newPatientId, int newDoctorId, String patientVisitInstructions, String Diagnosis1, String Diagnosis2, String Diagnosis3) throws PatientVisitNotFoundException, PatientNotFoundException, DoctorNotFoundException;

    public void deletePatientVisit(int id) throws PatientVisitNotFoundException;

    public Long countVisitsByPatientId(int patientId) throws PatientNotFoundException;

    public Long countVisitsByDoctorId(int doctorId) throws DoctorNotFoundException;

    public List<PatientVisitEntity> findVisitsByDiagnosis(String diagnosis) throws PatientVisitNotFoundException;

    public List<PatientVisitEntity> findVisitsWithMissingDiagnoses() throws PatientVisitNotFoundException;

}
