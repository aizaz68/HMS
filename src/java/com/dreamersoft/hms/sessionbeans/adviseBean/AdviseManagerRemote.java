/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.adviseBean;

import com.dreamersoft.hms.entity.AdviseEntity;
import com.dreamersoft.hms.sessionbeans.doctorMedicineBean.DoctorMedicineNotFoundException;
import com.dreamersoft.hms.sessionbeans.dosageBean.DosageNotFoundException;
import com.dreamersoft.hms.sessionbeans.patientVisitBean.PatientVisitNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface AdviseManagerRemote {

    public AdviseEntity getAdviseByID(int adviseID) throws AdviseNotFoundException;

    public AdviseEntity getAdviseByPatientVisitIDandDoctorMedicineID(int patientVisitID, int doctorMedicineID) throws AdviseNotFoundException, PatientVisitNotFoundException, DoctorMedicineNotFoundException;

    public List<AdviseEntity> getAdviseListByPatientVisitID(int patientVisitID) throws AdviseNotFoundException;

    public List<AdviseEntity> getAllAdviseList() throws AdviseNotFoundException;

    public AdviseEntity getAdviseByDosageIDandDoctorMedicineIDandPatientVisitID(int dosageID, int doctorMedicineID, int patientVisitID) throws AdviseNotFoundException, DosageNotFoundException, DoctorMedicineNotFoundException, PatientVisitNotFoundException;

    public AdviseEntity addNewAdvise(int dosageID, int medicineDays, int patientVisitID, int doctorMedicineID) throws InvalidMedicineDaysException, AdviseAlreadyExistsException, DosageNotFoundException, DoctorMedicineNotFoundException, PatientVisitNotFoundException;

    public int checkMedicineDays(int medicineDays) throws InvalidMedicineDaysException;

    public AdviseEntity updateAdviseEntity(int adviseID, int newDosageID, int newMedicineDays, int newPatientVisitID, int newDoctorMedicineID) throws InvalidMedicineDaysException, DuplicateAdviseException, AdviseNotFoundException, DosageNotFoundException, DoctorMedicineNotFoundException, DoctorMedicineNotFoundException, PatientVisitNotFoundException;

    public AdviseEntity deleteAdviseEntity(int adviseID) throws AdviseNotFoundException;


}
