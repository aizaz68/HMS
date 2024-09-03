/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.patientVsitTestBean;

import com.dreamersoft.hms.entity.PatientVisitTestEntity;
import com.dreamersoft.hms.sessionbeans.patientVisitBean.PatientVisitNotFoundException;
import com.dreamersoft.hms.sessionbeans.testBean.TestNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface PatientVisitTestManagerRemote {

    public PatientVisitTestEntity getPatientVisitTestByID(int patientVisitTestID) throws PatientVisitTestNotFoundException;

    public PatientVisitTestEntity getPatientVisitTestByTestIDandPatientVisitID(int testID, int patientVisitID) throws TestNotFoundException, PatientVisitTestNotFoundException, PatientVisitNotFoundException;

    public List<PatientVisitTestEntity> getPatientVisitTestListByPatientVisitID(int patientVisitID) throws PatientVisitTestNotFoundException, PatientVisitNotFoundException;

    public List<PatientVisitTestEntity> getPatientVisitTestListByTestID(int testID) throws TestNotFoundException, PatientVisitTestNotFoundException;

    public List<PatientVisitTestEntity> getAllPatientVisitTestList() throws PatientVisitTestNotFoundException;

    public List<PatientVisitTestEntity> getAllActivePatientVisitTest() throws PatientVisitTestNotFoundException;

    public int countPatientVisitTestByPatientVisitID(int patientVisitID) throws PatientVisitTestNotFoundException;

    public PatientVisitTestEntity deletePatientVisitTestByID(int patientVisitTestID) throws PatientVisitTestNotFoundException;

    public PatientVisitTestEntity restorePatientVisitTestByID(int patientVisitTestID) throws PatientVisitTestNotFoundException;

    public PatientVisitTestEntity addNewPatientVisitTest(int testID, int patientVisitID) throws TestNotFoundException, PatientVisitNotFoundException, PatientVisitTestNotFoundException, PatientVisitTestAlreadyExistsException;

    public PatientVisitTestEntity updatePatientVisitTestByID(int patientVisitTestID, int newTestID, int newPatientVisitID) throws PatientVisitTestNotFoundException, TestNotFoundException, PatientVisitNotFoundException, DuplicatePatientVisitTestException;

   

}
