/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testResultsBean;

import com.dreamersoft.hms.entity.PatientVisitTestEntity;
import com.dreamersoft.hms.entity.TestResultsEntity;
import com.dreamersoft.hms.sessionbeans.medicineBean.InvalidDeletedStatusException;
import com.dreamersoft.hms.sessionbeans.patientVsitTestBean.PatientVisitTestManagerRemote;
import com.dreamersoft.hms.sessionbeans.patientVsitTestBean.PatientVisitTestNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author aizaz
 */
@Stateless
public class TestResultManager implements TestResultManagerRemote {
@EJB
PatientVisitTestManagerRemote patientVisitTestManager;
    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;
@Override
public TestResultsEntity addTestResult(double testResultValue, int patientVisitTestId, Date testResultDate, boolean isDeleted)
        throws InvalidTestResultValueException, InvalidPatientVisitTestIdException, InvalidTestResultDateException, InvalidDeletedStatusException,PatientVisitTestNotFoundException {

    if (testResultValue <= 0) {
        throw new InvalidTestResultValueException("The test result value must be greater than zero.");
    }

    if (patientVisitTestId <= 0) {
        throw new InvalidPatientVisitTestIdException("The patient visit test ID must be greater than zero.");
    }

    if (testResultDate == null) {
        throw new InvalidTestResultDateException("The test result date cannot be null.");
    }

    if (!(isDeleted == true || isDeleted == false)) {
        throw new InvalidDeletedStatusException("The deleted status value is invalid.");
    }

    TestResultsEntity testResult = new TestResultsEntity();
    testResult.setTestResultValue(testResultValue);
    PatientVisitTestEntity patientVisit;
    patientVisit = patientVisitTestManager.getPatientVisitTestByID(patientVisitTestId);
    testResult.setPatientVisitTestId(patientVisit);
    testResult.setTestResultDate(testResultDate);
    testResult.setIsDeleted(isDeleted);

    em.persist(testResult);
    return testResult;
}

    /**
     *
     * @param testResultId
     * @return
     * @throws InvalidTestResultIDException
     * @throws TestResultNotFoundException
     */
    @Override
    public TestResultsEntity getTestResultByID(int testResultId) throws InvalidTestResultIDException, TestResultNotFoundException {
    if (testResultId <= 0) {
        throw new InvalidTestResultIDException("The test result ID cannot be zero or negative.");
    }

    TestResultsEntity testResult = em.find(TestResultsEntity.class, testResultId);
    if (testResult == null) {
        throw new TestResultNotFoundException("No test result found with ID: " + testResultId);
    }
    return testResult;
}
@Override
public List<TestResultsEntity> getTestResultsByValue(double testResultValue) throws InvalidTestResultValueException, TestResultNotFoundException {
    if (testResultValue <= 0) {
        throw new InvalidTestResultValueException("The test result value must be greater than zero.");
    }

    TypedQuery<TestResultsEntity> query = em.createQuery("SELECT t FROM TestResultsEntity t WHERE t.testResultValue = :testResultValue", TestResultsEntity.class);
    query.setParameter("testResultValue", testResultValue);
    
    List<TestResultsEntity> testResults = query.getResultList();
    if (testResults == null || testResults.isEmpty()) {
        throw new TestResultNotFoundException("No test results found with the value: " + testResultValue);
    }
    return testResults;
}
@Override
public void deleteTestResult(int testResultId) throws InvalidTestResultIDException, TestResultNotFoundException {
    TestResultsEntity testResult = getTestResultByID(testResultId);
    if (testResult == null) {
        throw new TestResultNotFoundException("No test result found for deletion.");
    }
    em.remove(testResult);
}
@Override
public void updateTestResult(int testResultId, double newTestResultValue, int patientVisitTestId, Date newTestResultDate, boolean isDeleted)
        throws InvalidTestResultIDException, InvalidTestResultValueException, InvalidPatientVisitTestIdException, InvalidTestResultDateException, InvalidDeletedStatusException, TestResultNotFoundException, TestResultNotUpdatedException {

    if (testResultId <= 0) {
        throw new InvalidTestResultIDException("The test result ID cannot be zero or negative.");
    }

    if (newTestResultValue <= 0) {
        throw new InvalidTestResultValueException("The new test result value must be greater than zero.");
    }

    if (patientVisitTestId <= 0) {
        throw new InvalidPatientVisitTestIdException("The patient visit test ID must be greater than zero.");
    }

    if (newTestResultDate == null) {
        throw new InvalidTestResultDateException("The test result date cannot be null.");
    }

    if (!(isDeleted == true || isDeleted == false)) {
        throw new InvalidDeletedStatusException("The deleted status value is invalid.");
    }

    TestResultsEntity testResult = em.find(TestResultsEntity.class, testResultId);
    if (testResult == null) {
        throw new TestResultNotFoundException("No test result found with ID: " + testResultId);
    }

    boolean isUpdated = false;

    if (newTestResultValue > 0) {
        testResult.setTestResultValue(newTestResultValue);
        isUpdated = true;
    }

    if (patientVisitTestId > 0) {
         PatientVisitTestEntity patientVisit;
        try {
            patientVisit = patientVisitTestManager.getPatientVisitTestByID(patientVisitTestId);
             testResult.setPatientVisitTestId(patientVisit);
        } catch (PatientVisitTestNotFoundException ex) {
            Logger.getLogger(TestResultManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        isUpdated = true;
    }

//    if (newTestResultDate != null) {
        testResult.setTestResultDate(newTestResultDate);
//        isUpdated = true;
//    }

    if (isDeleted == true || isDeleted == false) {
        testResult.setIsDeleted(isDeleted);
        isUpdated = true;
    }

    if (!isUpdated) {
        throw new TestResultNotUpdatedException("Update Failed! Please enter at least one value to update.");
    }

    em.merge(testResult);
}

@Override
public List<TestResultsEntity> getTestResultByPatientVisitId(int patientVisitId) throws InvalidPatientVisitTestIdException, TestResultNotFoundException, PatientVisitTestNotFoundException {
    if (patientVisitId <= 0) {
        throw new InvalidPatientVisitTestIdException("The patient visit test ID must be greater than zero.");
    }

    // Fetch the PatientVisitTestEntity by ID to ensure it exists
    PatientVisitTestEntity patientVisitTest = patientVisitTestManager.getPatientVisitTestByID(patientVisitId);
    
    // Query to get all test results associated with this PatientVisitTestEntity
    TypedQuery<TestResultsEntity> query = em.createQuery(
        "SELECT t FROM TestResultsEntity t WHERE t.patientVisitTestId = :patientVisitTestId", TestResultsEntity.class);
    query.setParameter("patientVisitTestId", patientVisitTest);

    List<TestResultsEntity> testResults = query.getResultList();

    if (testResults == null || testResults.isEmpty()) {
        throw new TestResultNotFoundException("No test results found for the patient visit test ID: " + patientVisitId);
    }

    return testResults;
}
    public void persist(Object object) {
        em.persist(object);
    }

    
}