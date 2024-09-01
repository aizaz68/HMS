/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorMedicineBean;


import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.DoctorMedicineEntity;
import com.dreamersoft.hms.entity.DosageEntity;
import com.dreamersoft.hms.entity.MedicineEntity;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorManagerRemote;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorNotFoundException;
import com.dreamersoft.hms.sessionbeans.dosageBean.DosageNotFoundException;
import com.dreamersoft.hms.sessionbeans.dosageBean.DosageManagerRemote;
import com.dreamersoft.hms.sessionbeans.medicineBean.MedicineManagerRemote;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author aizaz
 */
@Stateless
public class DoctorMedicineManager implements DoctorMedicineManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    DoctorManagerRemote doctorManager;

    @EJB
    MedicineManagerRemote medicineManager;

    @EJB
    DosageManagerRemote dosageManager;

    @Override
    public DoctorMedicineEntity getDoctorMedicineByID(int doctorMedicineID) throws DoctorMedicineNotFoundException {
        try {
            Query qry = em.createQuery("SELECT dm FROM DoctorMedicineEntity dm WHERE dm.doctorMedicineId = :doctorMedicineID AND dm.isDeleted = false");
            qry.setParameter("doctorMedicineID", doctorMedicineID);
            return (DoctorMedicineEntity) qry.getSingleResult();
        } catch (NoResultException ex) {
            throw new DoctorMedicineNotFoundException("Doctor Medicine Not Found For This ID: " + doctorMedicineID);
        } catch (Exception ex) {
            throw new DoctorMedicineNotFoundException("An unexpected error occurred while fetching Doctor Medicine: " + ex.getMessage());
        }
    }

    @Override
    public List<DoctorMedicineEntity> getDoctorMedicinesByDoctorID(int doctorID) throws DoctorMedicineNotFoundException, DoctorNotFoundException {
        try {
            DoctorEntity doctorEntity;
            doctorEntity=doctorManager.getDoctorByID(doctorID);
            if (doctorEntity == null){
                throw new DoctorNotFoundException("Doctor Not Found For This "+doctorID);
            }
            Query qry = em.createQuery("SELECT dm FROM DoctorMedicineEntity dm WHERE dm.doctorId.doctorId = :doctorID AND dm.isDeleted = false");
            qry.setParameter("doctorID", doctorID);
            List<DoctorMedicineEntity> doctorMedicineList = qry.getResultList();
            if (doctorMedicineList.isEmpty()) {
                throw new DoctorMedicineNotFoundException("No medicines found for Doctor ID: " + doctorID);
            }
            return doctorMedicineList;
        } catch (NoResultException ex) {
            throw new DoctorMedicineNotFoundException("Doctor with ID " + doctorID + " not found.");
        } catch (DoctorMedicineNotFoundException ex) {
            throw new DoctorMedicineNotFoundException("An unexpected error occurred while fetching medicines for Doctor ID: " + ex.getMessage());
        }
    }

    @Override
    public boolean checkMedicineExistence(int doctorID, int medicineID) throws DoctorMedicineNotFoundException {
        try {
            Query qry = em.createQuery("SELECT COUNT(dm) FROM DoctorMedicineEntity dm WHERE dm.doctorId.doctorId = :doctorID AND dm.medicineId.medicineId = :medicineID AND dm.isDeleted = false");
            qry.setParameter("doctorID", doctorID);
            qry.setParameter("medicineID", medicineID);
            long count = (long) qry.getSingleResult();
            return count > 0;
        } catch (Exception ex) {
            throw new DoctorMedicineNotFoundException("An unexpected error occurred while checking medicine existence: " + ex.getMessage());
        }
    }

    @Override
    public DoctorMedicineEntity addDoctorMedicine(int doctorID, int medicineID, int dosageID, int defaultDays) 
            throws DoctorNotFoundException, MedicineNotFoundException, DosageNotFoundException, DoctorMedicineAlreadyExistsException, DoctorMedicineNotFoundException {
        try {
            DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorID);
            MedicineEntity medicineEntity = medicineManager.getMedicineByID(medicineID);
            DosageEntity dosageEntity = dosageManager.getDosageByID(dosageID);

            if (checkMedicineExistence(doctorID, medicineID)) {
                throw new DoctorMedicineAlreadyExistsException("This Medicine is already assigned to the Doctor with ID: " + doctorID);
            }

            DoctorMedicineEntity doctorMedicineEntity = new DoctorMedicineEntity();
            doctorMedicineEntity.setDoctorId(doctorEntity);
            doctorMedicineEntity.setMedicineId(medicineEntity);
            doctorMedicineEntity.setDosageId(dosageEntity);
            doctorMedicineEntity.setDefaultDays(defaultDays);

            em.persist(doctorMedicineEntity);
            return doctorMedicineEntity;
        } catch (DoctorMedicineAlreadyExistsException ex) {
            throw ex; // Re-throw known exceptions
        } catch (DoctorMedicineNotFoundException ex) {
            throw new DoctorMedicineNotFoundException("An unexpected error occurred while adding Doctor Medicine: " + ex.getMessage());
        }
    }

    @Override
    public DoctorMedicineEntity updateDoctorMedicine(int doctorMedicineID, int doctorID, int medicineID, int dosageID, int defaultDays) 
            throws DoctorMedicineNotFoundException, DoctorNotFoundException, MedicineNotFoundException, DosageNotFoundException, DoctorMedicineAlreadyExistsException {
        try {
            DoctorMedicineEntity doctorMedicineEntity = getDoctorMedicineByID(doctorMedicineID);

            if (!checkMedicineExistence(doctorID, medicineID)) {
                DoctorEntity doctorEntity = doctorManager.getDoctorByID(doctorID);
                MedicineEntity medicineEntity = medicineManager.getMedicineByID(medicineID);
                DosageEntity dosageEntity = dosageManager.getDosageByID(dosageID);
                doctorMedicineEntity.setDoctorId(doctorEntity);
                doctorMedicineEntity.setMedicineId(medicineEntity);
                doctorMedicineEntity.setDosageId(dosageEntity);
                doctorMedicineEntity.setDefaultDays(defaultDays);
                em.merge(doctorMedicineEntity);
            } else {
                throw new DoctorMedicineAlreadyExistsException("This Medicine is already assigned to the Doctor with ID: " + doctorID);
            }

            return doctorMedicineEntity;
        } catch (DoctorMedicineNotFoundException | DoctorMedicineAlreadyExistsException ex) {
            throw ex; // Re-throw known exceptions
        }
    }

    @Override
    public void deleteDoctorMedicineByID(int doctorMedicineID) throws DoctorMedicineNotFoundException {
        try {
            DoctorMedicineEntity doctorMedicineEntity = getDoctorMedicineByID(doctorMedicineID);
            doctorMedicineEntity.setIsDeleted(true);
            em.merge(doctorMedicineEntity);
        } catch (NoResultException ex) {
            throw new DoctorMedicineNotFoundException("Doctor Medicine Not Found For This ID: " + doctorMedicineID);
        } catch (DoctorMedicineNotFoundException ex) {
            throw new DoctorMedicineNotFoundException("An unexpected error occurred while deleting Doctor Medicine: " + ex.getMessage());
        } 
    }
         
    @Override
    public int countMedicinesPrescribedByDoctor(int doctorId) throws DoctorMedicineNotFoundException {
        TypedQuery<Long> query;
        query = em.createQuery(
                "SELECT COUNT(DISTINCT dm.medicineId) FROM DoctorMedicineEntity dm WHERE dm.doctorId.doctorId = :doctorId", Long.class);
        query.setParameter("doctorId", doctorId);
        int intValue = query.getSingleResult().intValue();
        if (intValue>0){
            throw new DoctorMedicineNotFoundException("Doctor Medicine Not Found For This doctorId: " + doctorId);
        }
        return intValue;
    }
    

    public void persist(Object object) {
        em.persist(object);
        
    }
}



    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")}
