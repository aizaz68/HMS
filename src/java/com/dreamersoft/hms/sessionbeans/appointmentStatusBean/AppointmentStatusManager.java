/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.appointmentStatusBean;

import com.dreamersoft.hms.entity.AppointmentStatusEntity;
import java.util.List;
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
public class AppointmentStatusManager implements AppointmentStatusManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @Override
    public AppointmentStatusEntity getAppointmentStatusById(int appointmentStatusId) throws AppointmentStatusNotFoundException {
        AppointmentStatusEntity status;
        Query qry = em.createQuery("SELECT a FROM AppointmentStatusEntity a WHERE a.isDeleted = false AND a.appointmentStatusId = :appointmentStatusId");
        qry.setParameter("appointmentStatusId", appointmentStatusId);
        status = (AppointmentStatusEntity) qry.getSingleResult();
        if (status == null) {
            throw new AppointmentStatusNotFoundException("No Appointment Status found with id: " + appointmentStatusId);
        }
        return status;
    }

    @Override
    public void addAppointmentStatus(String appointmentStatusName) throws NullAppointmentNameException, DuplicateAppointmentStatusNameException {
        AppointmentStatusEntity status = new AppointmentStatusEntity();
        if (appointmentStatusName == null) {
            throw new NullAppointmentNameException("Student Name cannot be null");
        } else if (statusExists(appointmentStatusName)) {
            throw new DuplicateAppointmentStatusNameException("Appointment status already exists for name: " + appointmentStatusName);
        }
        status.setAppointmentStatusName(appointmentStatusName);
        em.persist(status);
    }

    @Override
    public List<AppointmentStatusEntity> findAllActiveAppointmentStatuses() throws AppointmentStatusNotFoundException {
        List<AppointmentStatusEntity> statuses;
        Query qry = em.createQuery("SELECT a FROM AppointmentStatusEntity a WHERE a.isDeleted = false");
        statuses = qry.getResultList();
        if (statuses.isEmpty()) {
            throw new AppointmentStatusNotFoundException("No Active Appointment Status found");
        }
        return statuses;
    }

    @Override
    public AppointmentStatusEntity findStatusByName(String statusName) throws AppointmentStatusNotFoundException {
        AppointmentStatusEntity status;
        Query qry = em.createQuery("SELECT a FROM AppointmentStatusEntity a WHERE a.appointmentStatusName = :statusName AND a.isDeleted = false");
        qry.setParameter("statusName", statusName);
        status = (AppointmentStatusEntity) qry.getSingleResult();
        if (status == null) {
            throw new AppointmentStatusNotFoundException("No Appointment Status found for name: " + statusName);
        }
        return status;
    }

    @Override
    public boolean statusExists(String statusName) {
        Query qry = em.createQuery("SELECT COUNT(a) FROM AppointmentStatusEntity a WHERE a.appointmentStatusName = :statusName AND a.isDeleted = false");
        qry.setParameter("statusName", statusName);
        return (long) qry.getSingleResult() > 0;
    }

    @Override
    public Long countActiveStatuses() {
        try {
            Query qry = em.createQuery("SELECT COUNT(a) FROM AppointmentStatusEntity a WHERE a.isDeleted = false");
            return (Long) qry.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    @Override
    public List<AppointmentStatusEntity> findAllDeletedStatuses() throws AppointmentStatusNotFoundException {
        List<AppointmentStatusEntity> deletedStatuses;
        Query qry = em.createQuery("SELECT a FROM AppointmentStatusEntity a WHERE a.isDeleted = true");
        deletedStatuses = qry.getResultList();
        if (deletedStatuses.isEmpty()) {
            throw new AppointmentStatusNotFoundException("No Deleted Appointment Status found");
        }
        return deletedStatuses;
    }

    @Override
    public void updateAppointmentStatus(int statusId, String newStatusName) throws AppointmentStatusNotFoundException, NullAppointmentNameException, DuplicateAppointmentStatusNameException {
        AppointmentStatusEntity newAppointmentStatus = this.getAppointmentStatusById(statusId);
        if (newStatusName == null) {
            throw new NullAppointmentNameException("Student Name cannot be null");
        } else if (statusExists(newStatusName)) {
            throw new DuplicateAppointmentStatusNameException("Appointment status already exists for name: " + newStatusName);
        }
        newAppointmentStatus.setAppointmentStatusName(newStatusName);
        em.merge(newAppointmentStatus);
    }

    @Override
    public void deleteAppointmentStatus(int statusId) throws AppointmentStatusNotFoundException {
        AppointmentStatusEntity toBeDeletedStatus = em.find(AppointmentStatusEntity.class, statusId);
        if (toBeDeletedStatus != null) {
            toBeDeletedStatus.setIsDeleted(true);
            em.merge(toBeDeletedStatus);
        } else {
            throw new AppointmentStatusNotFoundException("No Appointment Status found for id: " + statusId);
        }
    }

    @Override
    public void restoreDeletedStatus(int statusId) throws AppointmentStatusNotFoundException {
        AppointmentStatusEntity status;
        Query qry = em.createQuery("SELECT a FROM AppointmentStatusEntity a WHERE a.isDeleted = true AND a.appointmentStatusId = :appointmentStatusId");
        qry.setParameter("appointmentStatusId", statusId);
        status = (AppointmentStatusEntity) qry.getSingleResult();
        if (status != null) {
            status.setIsDeleted(false);
            em.merge(status);
        } else {
            throw new AppointmentStatusNotFoundException("No Appointment Status found for id: " + statusId);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        em.persist(object);
    }
}
