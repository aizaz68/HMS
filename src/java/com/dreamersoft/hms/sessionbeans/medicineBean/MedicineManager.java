/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.medicineBean;

import com.dreamersoft.hms.entity.MedicineEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author aizaz
 */
@Stateless
public class MedicineManager implements MedicineManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    @Override
    public MedicineEntity getMedicineByID(int medicineID) throws InvalidMedicineIDException, MedicineNotFoundException {
    if (medicineID <= 0) {
        throw new InvalidMedicineIDException("The ID cannot be zero or negative.");
    }

    MedicineEntity medicine = em.find(MedicineEntity.class, medicineID);
    if (medicine == null) {
        throw new MedicineNotFoundException("No medicine found with ID: " + medicineID);
    }
    return medicine;
}

    @Override
  public MedicineEntity addMedicine(String medicineName, boolean isDeleted)
        throws NullMedicineNameException, InvalidDeletedStatusException {
    MedicineEntity medicine = new MedicineEntity();
    if (medicineName == null || medicineName.isEmpty()) {
        throw new NullMedicineNameException("The medicine name cannot be empty.");
    }

    if (!(isDeleted == true || isDeleted == false)) {
        throw new InvalidDeletedStatusException("The deleted status value is invalid.");
    }

    else{
    medicine.setMedicineName(medicineName);
    medicine.setIsDeleted(isDeleted);

    em.persist(medicine);}
    
    return medicine;
}

    @Override
   public List<MedicineEntity> getMedicineByName(String medicineName) throws NullMedicineNameException, MedicineNotFoundException {
    if (medicineName == null || medicineName.isEmpty()) {
        throw new NullMedicineNameException("The medicine name cannot be empty.");
    }
    
    TypedQuery<MedicineEntity> query = em.createQuery("SELECT m FROM MedicineEntity m WHERE m.medicineName = :medicineName", MedicineEntity.class);
    query.setParameter("medicineName", medicineName);
    
    List<MedicineEntity> medicines = query.getResultList();
    if (medicines == null || medicines.isEmpty()) {
        throw new MedicineNotFoundException("No medicines found with name: " + medicineName);
    }
    return medicines;
}
    @Override
    public void deleteMedicine(int medicineID) throws InvalidMedicineIDException, MedicineNotFoundException {
    MedicineEntity medicine = getMedicineByID(medicineID);
    if (medicine == null) {
        throw new MedicineNotFoundException("No medicine found for deletion.");
    }
    em.remove(medicine);
}
    @Override
    public void updateMedicine(int medicineID, String newMedicineName, boolean isDeleted)
        throws InvalidMedicineIDException, InvalidMedicineNameException, InvalidDeletedStatusException, MedicineNotFoundException, MedicineNotUpdatedException {

    if (medicineID <= 0) {
        throw new InvalidMedicineIDException("The ID cannot be zero or negative.");
    }

    if (newMedicineName == null || newMedicineName.isEmpty()) {
        throw new InvalidMedicineNameException("The medicine name cannot be empty.");
    }

    if (!(isDeleted == true || isDeleted == false)) {
        throw new InvalidDeletedStatusException("The deleted status value is invalid.");
    }

    MedicineEntity medicine = em.find(MedicineEntity.class, medicineID);
    if (medicine == null) {
        throw new MedicineNotFoundException("No medicine found with ID: " + medicineID);
    }

    boolean isUpdated = false;

    if (newMedicineName != null && !newMedicineName.isEmpty()) {
        medicine.setMedicineName(newMedicineName);
        isUpdated = true;
    }

    if (isDeleted == true || isDeleted == false) {
        medicine.setIsDeleted(isDeleted);
        isUpdated = true;
    }

    if (!isUpdated) {
        throw new MedicineNotUpdatedException("Update Failed! Please enter at least one value to update.");
    }

    em.merge(medicine);
}

}