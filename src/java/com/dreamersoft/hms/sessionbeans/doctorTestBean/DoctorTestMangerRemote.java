/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorTestBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.DoctorTestEntity;
import com.dreamersoft.hms.entity.TestEntity;
import com.dreamersoft.hms.sessionbeans.doctorBean.DoctorNotFoundException;
import com.dreamersoft.hms.sessionbeans.testBean.TestNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface DoctorTestMangerRemote {

    public DoctorTestEntity getDoctorTestByID(int doctorTestID) throws DoctorTestNotFoundException;

    public DoctorTestEntity addDoctorTest(int doctorId, int testId) throws TestNotFoundException, DoctorNotFoundException, DoctorTestNotFoundException, DoctorTestAlreadyExistException;

    public void deleteDoctorTest(int doctorTestId) throws DoctorTestNotFoundException;

    public void restoreDoctorTestById(int doctorTestId) throws DoctorTestNotFoundException;

    public DoctorTestEntity updateDoctorTest(int doctorTestId, int newDoctorId, int newTestId) throws DoctorTestNotFoundException, DoctorTestAlreadyExistException, TestNotFoundException, DoctorNotFoundException;

    public List<DoctorTestEntity> getAllActiveDoctorTest() throws DoctorTestNotFoundException;

    public List<TestEntity> getAllTestsByDoctorId(int doctorId) throws TestNotFoundException, DoctorNotFoundException;

    public List<DoctorEntity> getAllDoctorsByTestId(int testId) throws DoctorNotFoundException, TestNotFoundException;

    public List<DoctorTestEntity> getDoctorTestList() throws DoctorTestNotFoundException;
    
   
}
