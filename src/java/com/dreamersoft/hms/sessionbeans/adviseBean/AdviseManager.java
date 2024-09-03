/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.adviseBean;

import com.dreamersoft.hms.entity.AdviseEntity;
import com.dreamersoft.hms.entity.DoctorMedicineEntity;
import com.dreamersoft.hms.entity.DosageEntity;
import com.dreamersoft.hms.entity.PatientVisitEntity;
import com.dreamersoft.hms.sessionbeans.doctorMedicineBean.DoctorMedicineManagerRemote;
import com.dreamersoft.hms.sessionbeans.doctorMedicineBean.DoctorMedicineNotFoundException;
import com.dreamersoft.hms.sessionbeans.dosageBean.DosageManagerRemote;
import com.dreamersoft.hms.sessionbeans.dosageBean.DosageNotFoundException;
import com.dreamersoft.hms.sessionbeans.patientVisitBean.PatientVisitManagerRemote;
import com.dreamersoft.hms.sessionbeans.patientVisitBean.PatientVisitNotFoundException;
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

/**
 *
 * @author aizaz
 */
@Stateless
public class AdviseManager implements AdviseManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    PatientVisitManagerRemote patientVisitManager;

    @EJB
    DoctorMedicineManagerRemote doctorMedicineManager;

    @EJB
    DosageManagerRemote dosageManager;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public AdviseEntity getAdviseByID(int adviseID) throws AdviseNotFoundException {

        AdviseEntity adviseEntity = null;
        Query qry = em.createQuery("SELECT s From AdviseEntity s Where s.adviseId=:adviseID AND s.isDeleted=false");
        qry.setParameter("adviseID", adviseID);

        try {
            adviseEntity = (AdviseEntity) qry.getSingleResult();
        } catch (NoResultException e) {
            throw new AdviseNotFoundException("Advise with This AdviseID :" + adviseID + " does'nt Found");
        }

        return adviseEntity;

    }

    @Override
    public AdviseEntity getAdviseByPatientVisitIDandDoctorMedicineID(int patientVisitID, int doctorMedicineID) throws AdviseNotFoundException, PatientVisitNotFoundException, DoctorMedicineNotFoundException {

        PatientVisitEntity patientVisitEntity = patientVisitManager.getPatientVisitByID(patientVisitID);

        DoctorMedicineEntity doctorMedicine = doctorMedicineManager.getDoctorMedicineByID(doctorMedicineID);

        AdviseEntity adviseEntity = null;
        Query qry = em.createQuery("Select s From AdviseEntity s Where s.doctorMedicineId.doctorMedicineId=:doctorMedicineID AND s.patientVisitId.patientVisitId=:patientVisitID AND s.isDeleted=false");
        qry.setParameter("patientVisitID", patientVisitID);
        qry.setParameter("doctorMedicineID", doctorMedicineID);
        try {
            adviseEntity = (AdviseEntity) qry.getSingleResult();
        } catch (NoResultException e) {
            throw new AdviseNotFoundException("No Advise Found With this DoctorMedicineID : " + doctorMedicineID + " and PatientVisitID : " + patientVisitID);
        }

        return adviseEntity;

    }

    @Override
    public List<AdviseEntity> getAdviseListByPatientVisitID(int patientVisitID) throws AdviseNotFoundException {

        Query qry = em.createQuery("Select s From AdviseEntity s Where s.patientVisitId.patientVisitId=:patientVisitID  AND s.isDeleted=false");
        qry.setParameter("patientVisitID", patientVisitID);
        List<AdviseEntity> adviseList = qry.getResultList();
        if (adviseList.isEmpty()) {

            throw new AdviseNotFoundException("No Advise Found with this PatientVisitID : " + patientVisitID);
        }
        return adviseList;

    }

    @Override
    public List<AdviseEntity> getAllAdviseList() throws AdviseNotFoundException {

        Query qry = em.createQuery("Select s From AdviseEntity s Where s.isDeleted=false");
        List<AdviseEntity> adviseList = qry.getResultList();
        if (adviseList.isEmpty()) {
            throw new AdviseNotFoundException("No Advise Record Found ");
        }
        return adviseList;

    }

    @Override
    public AdviseEntity getAdviseByDosageIDandDoctorMedicineIDandPatientVisitID(int dosageID, int doctorMedicineID, int patientVisitID) throws AdviseNotFoundException, DosageNotFoundException, DoctorMedicineNotFoundException, PatientVisitNotFoundException {

        DosageEntity dosageEntity = dosageManager.getDosageByID(dosageID);
        DoctorMedicineEntity doctorMedicineEntity = doctorMedicineManager.getDoctorMedicineByID(doctorMedicineID);

        PatientVisitEntity patientVisitEntity = patientVisitManager.getPatientVisitByID(patientVisitID);

        AdviseEntity adviseEntity = new AdviseEntity();
        Query qry = em.createQuery("Select s From AdviseEntity s Where s.dosageId.dosageId=:newDosageID AND s.doctorMedicineId.doctorMedicineId=:newDoctorMedicineID AND s.patientVisitId.patientVisitId=:newPatientVisitID and s.isDeleted=false");
        qry.setParameter("dosage", dosageID);
        qry.setParameter("doctorMedicineID", doctorMedicineID);
        qry.setParameter("patientVisitID", patientVisitID);
        if (qry.getSingleResult() == null) {
            throw new AdviseNotFoundException("No Advise Found With this dosageID : " + dosageID + " and doctorMedicineID: " + doctorMedicineID + " and PatientVisitID : " + patientVisitID);

        }
        return adviseEntity;

    }

    @Override
    public int checkMedicineDays(int medicineDays) throws InvalidMedicineDaysException {
        if (medicineDays <= 0) {
            throw new InvalidMedicineDaysException("Invalid value for MedicineDays :" + medicineDays);
        }
        return medicineDays;
    }

    @Override
    public AdviseEntity addNewAdvise(int dosageID, int medicineDays, int patientVisitID, int doctorMedicineID) throws InvalidMedicineDaysException, AdviseAlreadyExistsException, DosageNotFoundException, DoctorMedicineNotFoundException, PatientVisitNotFoundException {

        this.checkMedicineDays(medicineDays);

        DosageEntity dosageEntity = dosageManager.getDosageByID(dosageID);
        DoctorMedicineEntity doctorMedicineEntity = doctorMedicineManager.getDoctorMedicineByID(doctorMedicineID);

        PatientVisitEntity patientVisitEntity = patientVisitManager.getPatientVisitByID(patientVisitID);
        AdviseEntity adviseEntity = null;
        try {
            this.getAdviseByDosageIDandDoctorMedicineIDandPatientVisitID(dosageID, doctorMedicineID, patientVisitID);
            throw new AdviseNotFoundException("Advise Already Exists with the provided values \n : " + "dosageID :" + dosageID + " \n PatientvisitID : " + patientVisitID + "\n DoctorMedicineID: " + doctorMedicineID);
        } catch (AdviseNotFoundException ex) {

            adviseEntity = new AdviseEntity();
            adviseEntity.setDoctorMedicineId(doctorMedicineEntity);
            adviseEntity.setDosageId(dosageEntity);
            adviseEntity.setMedicineDays(medicineDays);
            adviseEntity.setPatientVisitId(patientVisitEntity);
            em.persist(adviseEntity);
            em.flush();
            em.refresh(adviseEntity);

        }

        return adviseEntity;

    }

    @Override
    public AdviseEntity updateAdviseEntity(int adviseID, int newDosageID, int newMedicineDays, int newPatientVisitID, int newDoctorMedicineID) throws InvalidMedicineDaysException, DuplicateAdviseException, AdviseNotFoundException, DosageNotFoundException, DoctorMedicineNotFoundException, DoctorMedicineNotFoundException, PatientVisitNotFoundException {
        this.checkMedicineDays(newMedicineDays);

        AdviseEntity adviseEntityUpdate = this.getAdviseByID(adviseID);

        DosageEntity dosageEntity = dosageManager.getDosageByID(newDosageID);

        DoctorMedicineEntity doctorMedicineEntity = doctorMedicineManager.getDoctorMedicineByID(newDoctorMedicineID);

        PatientVisitEntity patientVisitEntity = patientVisitManager.getPatientVisitByID(newPatientVisitID);

        AdviseEntity adviseEntity = null;
        try {
            adviseEntity = this.getAdviseByDosageIDandDoctorMedicineIDandPatientVisitID(newDosageID, newDoctorMedicineID, newPatientVisitID);
            if (adviseEntity != null && (!Objects.equals(adviseEntity.getAdviseId(), adviseEntityUpdate.getAdviseId()))) {
                throw new DuplicateAdviseException("Advise Already Exists Against another Record with the provided values \n : " + "dosageID :" + newDosageID + " \n PatientvisitID : " + newPatientVisitID + "\n DoctorMedicineID: " + newDoctorMedicineID);
            }

        } catch (AdviseNotFoundException ex) {
            adviseEntityUpdate.setDosageId(dosageEntity);
            adviseEntityUpdate.setDoctorMedicineId(doctorMedicineEntity);
            adviseEntityUpdate.setMedicineDays(newMedicineDays);
            adviseEntityUpdate.setPatientVisitId(patientVisitEntity);
            em.persist(adviseEntityUpdate);
            em.flush();
            em.refresh(adviseEntityUpdate);
        }

        return adviseEntityUpdate;

    }

    @Override
    public AdviseEntity deleteAdviseEntity(int adviseID) throws AdviseNotFoundException {

        AdviseEntity adviseEntity = this.getAdviseByID(adviseID);

        adviseEntity.setIsDeleted(true);
        em.persist(adviseEntity);
        em.flush();
        em.refresh(adviseEntity);
        return adviseEntity;
    }
}
