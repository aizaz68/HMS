/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testResultsBean;

import com.dreamersoft.hms.entity.TestResultsEntity;
import com.dreamersoft.hms.sessionbeans.medicineBean.InvalidDeletedStatusException;
import com.dreamersoft.hms.sessionbeans.patientVsitTestBean.PatientVisitTestNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface TestResultManagerRemote {

    public TestResultsEntity getTestResultByID(int testResultId) throws InvalidTestResultIDException, TestResultNotFoundException;

    public List<TestResultsEntity> getTestResultsByValue(double testResultValue) throws InvalidTestResultValueException, TestResultNotFoundException;

    public void deleteTestResult(int testResultId) throws InvalidTestResultIDException, TestResultNotFoundException;

    public void updateTestResult(int testResultId, double newTestResultValue, int patientVisitTestId, Date newTestResultDate, boolean isDeleted) throws InvalidTestResultIDException, InvalidTestResultValueException, InvalidPatientVisitTestIdException, InvalidTestResultDateException, InvalidDeletedStatusException, TestResultNotFoundException, TestResultNotUpdatedException;

    public TestResultsEntity addTestResult(double testResultValue, int patientVisitTestId, Date testResultDate, boolean isDeleted) throws InvalidTestResultValueException, InvalidPatientVisitTestIdException, InvalidTestResultDateException, InvalidDeletedStatusException, PatientVisitTestNotFoundException;

    public List<TestResultsEntity> getTestResultByPatientVisitId(int patientVisitId) throws InvalidPatientVisitTestIdException, TestResultNotFoundException, PatientVisitTestNotFoundException;
    


}
