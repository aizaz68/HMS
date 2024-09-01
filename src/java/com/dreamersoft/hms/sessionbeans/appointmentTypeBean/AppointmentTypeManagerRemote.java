/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.appointmentTypeBean;

import com.dreamersoft.hms.entity.AppointmentTypeEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface AppointmentTypeManagerRemote {

    public AppointmentTypeEntity getAppointmentTypeByID(int appointmentTypeID) throws AppointmentTypeNotFoundException;

}
