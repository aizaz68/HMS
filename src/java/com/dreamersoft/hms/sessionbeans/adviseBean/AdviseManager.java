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
    public AdviseEntity getAdviseByPatientVisitIDandDoctorMedicineID(int patientVisitID, int doctorMedicineID) throws AdviseNotFoundException {

        try {
            PatientVisitEntity patientVisitEntity = patientVisitManager.getPatientVisitByID(patientVisitID);
        } catch (PatientVisitNotFoundException ex) {
            Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            DoctorMedicineEntity doctorMedicine = doctorMedicineManager.getDoctorMedicineByID(doctorMedicineID);
        } catch (DoctorMedicineNotFoundException ex) {
            Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        em.persist(object);
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
    public AdviseEntity addNewAdvise(int dosageID, int medicineDays, int patientVisitID, int doctorMedicineID) throws InvalidMedicineDaysException, AdviseAlreadyExistsException {
        if (medicineDays <= 0) {
            throw new InvalidMedicineDaysException("Invalid value for MedicineDays :" + medicineDays);
        }

        DosageEntity dosageEntity = new DosageEntity();
        DoctorMedicineEntity doctorMedicineEntity = new DoctorMedicineEntity();
        PatientVisitEntity patientVisitEntity = new PatientVisitEntity();
        try {
            dosageEntity = dosageManager.getDosageByID(dosageID);
        } catch (DosageNotFoundException ex) {
            Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            doctorMedicineEntity = doctorMedicineManager.getDoctorMedicineByID(doctorMedicineID);
        } catch (DoctorMedicineNotFoundException ex) {
            Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            patientVisitEntity = patientVisitManager.getPatientVisitByID(patientVisitID);
        } catch (PatientVisitNotFoundException ex) {
            Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        AdviseEntity adviseEntity ;
        Query qry = em.createQuery("Select s From AdviseEntity s Where s.dosageId.dosageId=:newDosageID AND s.doctorMedicineId.doctorMedicineId=:newDoctorMedicineID AND s.patientVisitId.patientVisitId=:newPatientVisitID and s.isDeleted=false");
        qry.setParameter("dosage", dosageID);
        qry.setParameter("doctorMedicineID", doctorMedicineID);
        qry.setParameter("patientVisitID", patientVisitID);
        adviseEntity = (AdviseEntity) qry.getSingleResult();
        if (adviseEntity != null) {
            throw new AdviseAlreadyExistsException("Advise Already Exists with the provided values \n : " + "dosageID :" + dosageID + " \n PatientvisitID : " + patientVisitID + "\n DoctorMedicineID: " + doctorMedicineID);
        }
        adviseEntity = new AdviseEntity();
        adviseEntity.setDoctorMedicineId(doctorMedicineEntity);
        adviseEntity.setDosageId(dosageEntity);
        adviseEntity.setMedicineDays(medicineDays);
        adviseEntity.setPatientVisitId(patientVisitEntity);
        em.persist(adviseEntity);
        em.flush();
        em.refresh(adviseEntity);

        return adviseEntity;

    }

    @Override
    public AdviseEntity updateAdviseEntity(int adviseID, int newDosageID, int newMedicineDays, int newPatientVisitID, int newDoctorMedicineID) throws InvalidMedicineDaysException,DuplicateAdviseException {
     if (newMedicineDays <= 0) {
            throw new InvalidMedicineDaysException("Invalid value for MedicineDays :" + newMedicineDays);
        }
     
     AdviseEntity adviseEntityUpdate=new AdviseEntity();
     
     
     try{
     adviseEntityUpdate=this.getAdviseByID(adviseID);
     }catch(AdviseNotFoundException ex){
     Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
     }

        DosageEntity dosageEntity =new DosageEntity();
        DoctorMedicineEntity doctorMedicineEntity=new DoctorMedicineEntity() ;
        PatientVisitEntity patientVisitEntity=new PatientVisitEntity() ;
        try {
            dosageEntity = dosageManager.getDosageByID(newDosageID);
        } catch (DosageNotFoundException ex) {
            Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            doctorMedicineEntity = doctorMedicineManager.getDoctorMedicineByID(newDoctorMedicineID);
        } catch (DoctorMedicineNotFoundException ex) {
            Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            patientVisitEntity = patientVisitManager.getPatientVisitByID(newPatientVisitID);
        } catch (PatientVisitNotFoundException ex) {
            Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        AdviseEntity adviseEntity = null;
        Query qry = em.createQuery("Select s From AdviseEntity s Where s.dosageId.dosageId=:dosageID AND s.doctorMedicineId.doctorMedicineId=:doctorMedicineID AND s.patientVisitId.patientVisitId=:patientVisitID and s.isDeleted=false");
        qry.setParameter("newDosage", newDosageID);
        qry.setParameter("newDoctorMedicineID", newDoctorMedicineID);
        qry.setParameter("newPatientVisitID", newPatientVisitID);
        adviseEntity = (AdviseEntity) qry.getSingleResult();
        if (adviseEntity != null&& (!Objects.equals(adviseEntity.getAdviseId(), adviseEntityUpdate.getAdviseId()))) {
            throw new DuplicateAdviseException("Advise Already Exists Against another Record with the provided values \n : " + "dosageID :" + newDosageID + " \n PatientvisitID : " + newPatientVisitID + "\n DoctorMedicineID: " + newDoctorMedicineID);
        }
       
        adviseEntityUpdate.setDosageId(dosageEntity);
        adviseEntityUpdate.setDoctorMedicineId(doctorMedicineEntity);
        adviseEntityUpdate.setMedicineDays(newMedicineDays);
        adviseEntityUpdate.setPatientVisitId(patientVisitEntity);
        em.persist(adviseEntityUpdate);
        em.flush();
        em.refresh(adviseEntityUpdate);

        return adviseEntityUpdate;

    
    
    }

    @Override
    public AdviseEntity deleteAdviseEntity(int adviseID) throws AdviseNotFoundException {
    
        AdviseEntity adviseEntity=new AdviseEntity();
        
        try{
        adviseEntity=this.getAdviseByID(adviseID);
        }
        catch(AdviseNotFoundException ex){
          Logger.getLogger(AdviseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        adviseEntity.setIsDeleted(true);
        em.persist(adviseEntity);
        em.flush();
        em.refresh(adviseEntity);
    return adviseEntity;
    }
}
