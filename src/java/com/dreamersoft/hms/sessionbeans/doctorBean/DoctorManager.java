/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import javax.ejb.Stateless;

/**
 *
 * @author aizaz
 */
@Stateless
public class DoctorManager implements DoctorManagerRemote {

    @Override
    public DoctorEntity getDoctorByID(int doctorID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
