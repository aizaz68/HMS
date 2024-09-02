            /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface DoctorManagerRemote {
    public DoctorEntity getDoctorByID(int doctorID)throws DoctorNotFoundException;
    
}
