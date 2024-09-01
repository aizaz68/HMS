/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.patientBean;

import com.dreamersoft.hms.entity.PatientEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface PatientManagerRemote {

public PatientEntity getPatientByID(int patientID)throws PatientNotFoundException;




    
}
