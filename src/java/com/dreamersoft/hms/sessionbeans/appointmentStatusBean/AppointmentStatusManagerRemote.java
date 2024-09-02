/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.appointmentStatusBean;

import com.dreamersoft.hms.entity.AppointmentStatusEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface AppointmentStatusManagerRemote {

    public AppointmentStatusEntity getAppointmentStatusById(int appointmentStatusId) throws AppointmentStatusNotFoundException;

    public void addAppointmentStatus(String appointmentStatusName) throws NullAppointmentNameException, DuplicateAppointmentStatusNameException;

    public AppointmentStatusEntity findStatusByName(String statusName) throws AppointmentStatusNotFoundException;

    public boolean statusExists(String statusName);

    public Long countActiveStatuses();

    public List<AppointmentStatusEntity> findAllActiveAppointmentStatuses() throws AppointmentStatusNotFoundException;

    public void updateAppointmentStatus(int statusId, String newStatusName) throws AppointmentStatusNotFoundException, NullAppointmentNameException, DuplicateAppointmentStatusNameException;

    public void deleteAppointmentStatus(int id) throws AppointmentStatusNotFoundException;

    public List<AppointmentStatusEntity> findAllDeletedStatuses() throws AppointmentStatusNotFoundException;

    public void restoreDeletedStatus(int statusId) throws AppointmentStatusNotFoundException;
}
