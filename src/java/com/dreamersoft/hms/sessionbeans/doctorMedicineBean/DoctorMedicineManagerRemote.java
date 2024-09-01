/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorMedicineBean;

import com.dreamersoft.hms.entity.DoctorMedicineEntity;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorNotFoundException;
import com.dreamersoft.hms.sessionbeans.dosageBean.DosageNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface DoctorMedicineManagerRemote {


    public void deleteDoctorMedicineByID(int doctorMedicineID) throws DoctorMedicineNotFoundException;

    public DoctorMedicineEntity updateDoctorMedicine(int doctorMedicineID, int doctorID, int medicineID, int dosageID, int defaultDays) throws DoctorMedicineNotFoundException, DoctorNotFoundException, MedicineNotFoundException, DosageNotFoundException, DoctorMedicineAlreadyExistsException;

    public DoctorMedicineEntity getDoctorMedicineByID(int doctorMedicineID) throws DoctorMedicineNotFoundException;

    public boolean checkMedicineExistence(int doctorID, int medicineID) throws DoctorMedicineNotFoundException;

    public List<DoctorMedicineEntity> getDoctorMedicinesByDoctorID(int doctorID) throws DoctorMedicineNotFoundException, DoctorNotFoundException;

    public DoctorMedicineEntity addDoctorMedicine(int doctorID, int medicineID, int dosageID, int defaultDays) throws DoctorNotFoundException, MedicineNotFoundException, DosageNotFoundException, DoctorMedicineAlreadyExistsException, DoctorMedicineNotFoundException;

    public int countMedicinesPrescribedByDoctor(int doctorId) throws DoctorMedicineNotFoundException;

}
