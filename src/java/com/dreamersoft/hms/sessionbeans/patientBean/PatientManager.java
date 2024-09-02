/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.patientBean;

import com.dreamersoft.hms.entity.PatientEntity;
import javax.ejb.Stateless;

/**
 *
 * @author aizaz
 */
@Stateless
public class PatientManager implements PatientManagerRemote {

    @Override
    public PatientEntity getPatientByID(int patientID) {
        return new PatientEntity(1);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
