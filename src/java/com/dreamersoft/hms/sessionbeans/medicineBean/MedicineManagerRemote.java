/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.medicineBean;

import com.dreamersoft.hms.entity.MedicineEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface MedicineManagerRemote {

    public MedicineEntity getMedicineByID(int medicineID) throws InvalidMedicineIDException, MedicineNotFoundException;

    public MedicineEntity addMedicine(String medicineName, boolean isDeleted) throws NullMedicineNameException, InvalidDeletedStatusException;

    public List<MedicineEntity> getMedicineByName(String medicineName) throws NullMedicineNameException, MedicineNotFoundException;

    public void deleteMedicine(int medicineID) throws InvalidMedicineIDException, MedicineNotFoundException;

    public void updateMedicine(int medicineID, String newMedicineName, boolean isDeleted) throws InvalidMedicineIDException, InvalidMedicineNameException, InvalidDeletedStatusException, MedicineNotFoundException, MedicineNotUpdatedException;



    
}
