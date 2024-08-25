/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorTestBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.DoctorTestEntity;
import com.dreamersoft.hms.entity.TestEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface DoctorTestMangerRemote {
    
    public DoctorTestEntity getDoctorTestByID(int doctorTestID) throws DoctorTestNotFoundException;

    public void addDoctorTest(DoctorEntity doctor, TestEntity test) throws NullDoctorNameException, NullTestNameException;

    public void deleteDoctorTest(int doctorTestId) throws DoctorTestNotFoundException;

    public void updateDoctorTest(int doctorTestId, DoctorEntity newDoctor, TestEntity newTest) throws DoctorTestNotFoundException;

    public List<DoctorTestEntity> getAllActiveDoctorTest() throws DoctorTestNotFoundException;

    public void restoreDoctorTest(int doctorTestId) throws DoctorTestNotFoundException;
}
