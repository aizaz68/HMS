/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorTestBean;

import com.dreamersoft.hms.entity.DoctorTestEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface DoctorTestMangerRemote {
    
public DoctorTestEntity getDoctorTestByID(int doctorTestID);

}
