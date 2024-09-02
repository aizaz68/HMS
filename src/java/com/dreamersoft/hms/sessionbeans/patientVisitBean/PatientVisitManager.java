/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.patientVisitBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.PatientEntity;
import com.dreamersoft.hms.entity.PatientVisitEntity;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorManagerRemote;
import com.dreamersoft.hms.sessionbeans.patientBean.PatientManagerRemote;
import java.util.Date;
import java.util.List;
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
public class PatientVisitManager implements PatientVisitManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    PatientManagerRemote patientManager;

    @EJB
    DoctorManagerRemote doctorManager;

    @Override
    public PatientVisitEntity getPatientVisitByID(int patientVisitID) throws PatientVisitNotFoundException {
        try {
            PatientVisitEntity patientVisit;
            Query qry = em.createQuery("SELECT pv FROM PatientVisitEntity pv WHERE pv.isDeleted = false AND pv.patientVisitId = :patientVisitId");
            qry.setParameter("patientVisitId", patientVisitID);
            patientVisit = (PatientVisitEntity) qry.getSingleResult();
            return patientVisit;
        } catch (NoResultException e) {
            throw new PatientVisitNotFoundException("No Patient Visit exists with ID: " + patientVisitID);
        }
    }

    @Override
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
            Integer doctorId) throws PatientNotFoundException, DoctorNotFoundException {

        PatientEntity patient = patientManager.getPatientByID(patientId);
        DoctorEntity doctor = doctorManager.getDoctorByID(doctorId);

        if (patient == null) {
            throw new PatientNotFoundException("No Patient found of Id: " + patientId);
        } else if (doctor == null) {
            throw new DoctorNotFoundException("No Doctor found of Id:" + doctorId);
        }

        PatientVisitEntity patientVisit = new PatientVisitEntity();

        patientVisit.setPatientVisitDate(patientVisitDate);
        patientVisit.setPatientBloodPressure(patientBloodPressure);
        patientVisit.setPatientPulse(patientPulse);
        patientVisit.setPatientTemperature(patientTemperature);
        patientVisit.setPatientWeight(patientWeight);
        patientVisit.setPatientAge(patientAge);
        patientVisit.setDiagnosis1(diagnosis1);
        patientVisit.setDiagnosis2(diagnosis2);
        patientVisit.setDiagnosis3(diagnosis3);
        patientVisit.setPatientVisitInstructions(patientVisitInstructions);

        patientVisit.setPatientId(patient);
        patientVisit.setDoctorId(doctor);

        persist(patientVisit);
    }

    @Override
    public List<PatientVisitEntity> findAllPatientVisitsByPatientIDAndDoctorID(int patientID, int doctorID) throws PatientVisitNotFoundException, PatientNotFoundException, DoctorNotFoundException {
        PatientEntity patient = patientManager.getPatientByID(patientID);
        DoctorEntity doctor = doctorManager.getDoctorByID(doctorID);
        if (patient == null) {
            throw new PatientNotFoundException("No Patient found of Id: " + patientID);
        } else if (doctor == null) {
            throw new DoctorNotFoundException("No Doctor found of Id:" + doctorID);
        }
        List<PatientVisitEntity> patientVisits;
        Query qry = em.createQuery("SELECT pv FROM PatientVisitEntity pv WHERE pv.isDeleted = false AND pv.patientId.patientId = :patientId AND pv.doctorId.doctorId = :doctorId");
        qry.setParameter("patientId", patientID);
        qry.setParameter("doctorId", doctorID);
        patientVisits = qry.getResultList();
        if (patientVisits.isEmpty()) {
            throw new PatientVisitNotFoundException("No patient visit found for pateint ID: " + patientID + " and doctor ID: " + doctorID);
        }
        return patientVisits;
    }

    @Override
    public List<PatientVisitEntity> findAllPatientVisitsByPatientID(int patientID) throws PatientVisitNotFoundException, PatientNotFoundException {
        PatientEntity patient = patientManager.getPatientByID(patientID);
        if (patient == null) {
            throw new PatientNotFoundException("No Patient found of Id: " + patientID);
        }
        List<PatientVisitEntity> patientVisits;
        Query qry = em.createQuery("SELECT pv FROM PatientVisitEntity pv WHERE pv.isDeleted = false AND pv.patientId.patientId = :patientId");
        qry.setParameter("patientId", patientID);
        patientVisits = qry.getResultList();
        if (patientVisits.isEmpty()) {
            throw new PatientVisitNotFoundException("No patient visit found for pateint ID: " + patientID);
        }
        return patientVisits;
    }

    @Override
    public List<PatientVisitEntity> findAllPatientVisitsByDoctorID(int doctorID) throws PatientVisitNotFoundException, DoctorNotFoundException {
        DoctorEntity doctor = doctorManager.getDoctorByID(doctorID);
        if (doctor == null) {
            throw new DoctorNotFoundException("No Doctor found of Id:" + doctorID);
        }
        List<PatientVisitEntity> patientVisits;
        Query qry = em.createQuery("SELECT pv FROM PatientVisitEntity pv WHERE pv.isDeleted = false AND pv.doctorId.doctorId = :doctorId");
        qry.setParameter("doctorId", doctorID);
        patientVisits = qry.getResultList();
        if (patientVisits.isEmpty()) {
            throw new PatientVisitNotFoundException("No patient visit found for doctor ID: " + doctorID);
        }
        return patientVisits;
    }

    @Override
    public PatientVisitEntity findLatestVisitByPatientId(int patientId) throws PatientNotFoundException, PatientVisitNotFoundException {
        PatientEntity patient = patientManager.getPatientByID(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("No Patient found of Id: " + patientId);
        }
        PatientVisitEntity latestVisit;
        Query qry = em.createQuery("SELECT pv FROM PatientVisitEntity pv WHERE pv.patientId.patientId = :patientId AND pv.isDeleted = false ORDER BY pv.patientVisitDate DESC");
        qry.setParameter("patientId", patientId);
        qry.setMaxResults(1);
        latestVisit = (PatientVisitEntity) qry.getSingleResult();
        if (latestVisit == null) {
            throw new PatientVisitNotFoundException("No patient visit found for pateint ID: " + patientId);
        }
        return latestVisit;
    }

    @Override
    public List<PatientVisitEntity> findVisitsByDateRange(Date startDate, Date endDate) throws PatientVisitNotFoundException {
        List<PatientVisitEntity> patientVisits;
        Query qry = em.createQuery("SELECT pv FROM PatientVisitEntity pv WHERE pv.patientVisitDate BETWEEN :startDate AND :endDate AND pv.isDeleted = false");
        qry.setParameter("startDate", startDate);
        qry.setParameter("endDate", endDate);
        patientVisits = qry.getResultList();
        if (patientVisits.isEmpty()) {
            throw new PatientVisitNotFoundException("No patient visit found for time period: " + startDate + " to " + endDate);
        }
        return patientVisits;
    }

    @Override
    public List<PatientVisitEntity> findVisitsByDiagnosis(String diagnosis) throws PatientVisitNotFoundException {
        List<PatientVisitEntity> patientVisits;
        Query qry = em.createQuery("SELECT pv FROM PatientVisitEntity pv WHERE (pv.diagnosis1 = :diagnosis OR pv.diagnosis2 = :diagnosis OR pv.diagnosis3 = :diagnosis) AND pv.isDeleted = false");
        qry.setParameter("diagnosis", diagnosis);
        patientVisits = qry.getResultList();
        if (patientVisits.isEmpty()) {
            throw new PatientVisitNotFoundException("No patient visit found for diagnosis: " + diagnosis);
        }
        return patientVisits;
    }

    @Override
    public Long countVisitsByDoctorId(int doctorId) throws DoctorNotFoundException {
        DoctorEntity doctor = doctorManager.getDoctorByID(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException("No Doctor found of Id:" + doctorId);
        }
        Long totalVisits;
        Query qry = em.createQuery("SELECT COUNT(pv) FROM PatientVisitEntity pv WHERE pv.doctorId.doctorId = :doctorId AND pv.isDeleted = false");
        qry.setParameter("doctorId", doctorId);
        totalVisits = (Long) qry.getSingleResult();
        return totalVisits;
    }

    @Override
    public Long countVisitsByPatientId(int patientId) throws PatientNotFoundException {
        PatientEntity patient = patientManager.getPatientByID(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("No Patient found of Id: " + patientId);
        }
        Long totalVisits;
        Query qry = em.createQuery("SELECT COUNT(pv) FROM PatientVisitEntity pv WHERE pv.patientId.patientId = :patientId AND pv.isDeleted = false");
        qry.setParameter("patientId", patientId);
        totalVisits = (Long) qry.getSingleResult();
        return totalVisits;
    }

    @Override
    public List<PatientVisitEntity> findVisitsWithMissingDiagnoses() throws PatientVisitNotFoundException {
        List<PatientVisitEntity> patientVisits;
        Query qry = em.createQuery("SELECT pv FROM PatientVisitEntity pv WHERE (pv.diagnosis1 IS NULL OR pv.diagnosis2 IS NULL OR pv.diagnosis3 IS NULL) AND pv.isDeleted = false");
        patientVisits = qry.getResultList();
        if (patientVisits.isEmpty()) {
            throw new PatientVisitNotFoundException("No patient visit found with missing diagnosis");
        }
        return patientVisits;
    }

    @Override
    public List<PatientVisitEntity> getAllPatientVisitList() throws PatientVisitNotFoundException {

        Query qry = em.createQuery("Select pv From PatientVisitEntity pv Where pv.isDeleted=false");
        List<PatientVisitEntity> patientVisits = qry.getResultList();
        if (patientVisits.isEmpty()) {
            throw new PatientVisitNotFoundException("No Patient Visit Record Found ");
        }
        return patientVisits;

    }

    @Override
    public void updatePatientVisit(int patientVisitId, int newPatientId, int newDoctorId, String patientVisitInstructions, String Diagnosis1, String Diagnosis2, String Diagnosis3) throws PatientVisitNotFoundException, PatientNotFoundException, DoctorNotFoundException {
        PatientVisitEntity newPatientVisit;
        newPatientVisit = this.getPatientVisitByID(patientVisitId);
        if (newPatientVisit == null) {
            throw new PatientVisitNotFoundException("No Patient visit found with ID:" + patientVisitId);
        }

        PatientEntity patient = patientManager.getPatientByID(newPatientId);
        DoctorEntity doctor = doctorManager.getDoctorByID(newDoctorId);

        if (patient == null) {
            throw new PatientNotFoundException("No Patient found of Id: " + newPatientId);
        } else if (doctor == null) {
            throw new DoctorNotFoundException("No Doctor found of Id:" + newDoctorId);
        }

        newPatientVisit.setDiagnosis1(Diagnosis1);
        newPatientVisit.setDiagnosis2(Diagnosis2);
        newPatientVisit.setDiagnosis3(Diagnosis3);
        newPatientVisit.setPatientVisitInstructions(patientVisitInstructions);
        newPatientVisit.setDoctorId(doctor);
        newPatientVisit.setPatientId(patient);
        em.merge(newPatientVisit);
    }

    @Override
    public void deletePatientVisit(int visitId) throws PatientVisitNotFoundException {
        PatientVisitEntity patientVisit = this.getPatientVisitByID(visitId);
        if (patientVisit != null) {
            patientVisit.setIsDeleted(true);
            em.merge(patientVisit);
        } else {
            throw new PatientVisitNotFoundException("Patient Visit not found with this ID: " + visitId);
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
