/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testDefaultValueBean;

import com.dreamersoft.hms.entity.TestDefaultValueEntity;
import com.dreamersoft.hms.sessionbeans.medicineBean.InvalidDeletedStatusException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author aizaz
 */
@Stateless
public class TestDefaultValueManager implements TestDefaultValueManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @Override
    public TestDefaultValueEntity AddTestDefaultValue(double testDefaultValue, boolean isMale, boolean isDeleted)
            throws InvalidTestValueException, InvalidGenderException, InvalidDeletedStatusException {
        TestDefaultValueEntity defaultTest = new TestDefaultValueEntity();
        if (testDefaultValue <= 0) {
            throw new InvalidTestValueException("The test default value must be greater than zero.");
        }

        if (!(isMale == true || isMale == false)) {
            throw new InvalidGenderException("The gender value is invalid.");
        }

        if (!(isDeleted == true || isDeleted == false)) {
            throw new InvalidDeletedStatusException("The deleted status value is invalid.");
        } else {

            defaultTest.setTestDefaultValue(testDefaultValue);
            defaultTest.setIsMale(isMale);
            defaultTest.setIsDeleted(isDeleted);

            em.persist(defaultTest);
        }
        return defaultTest;
    }

    @Override
    public TestDefaultValueEntity getTestDefaultValueByID(int testDefaultValueID) throws invalidTestValueIDException, TestDefaultValueNotFoundException {
        TestDefaultValueEntity testValue = em.find(TestDefaultValueEntity.class, testDefaultValueID);
        if (testDefaultValueID <= 0) {
            throw new invalidTestValueIDException("The ID you Entered is in valid");
        }

        if (testValue == null) {
            throw new TestDefaultValueNotFoundException("No Test value found against the id" + testDefaultValueID); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        return testValue;
    }

    @Override
    public List<TestDefaultValueEntity> getTestDefaultValueByValue(String testDefaultValue) throws testDefaultValueCannotBeEmptyException, TestDefaultValueNotFoundException {
        List<TestDefaultValueEntity> testValues;
        TypedQuery<TestDefaultValueEntity> query = em.createQuery("SELECT t FROM TestDefaultValueEntity t WHERE t.testDefaultValue = :testDefaultValue", TestDefaultValueEntity.class);
        query.setParameter("name", testDefaultValue);
        testValues = query.getResultList();
        if (testDefaultValue == null) {
            throw new testDefaultValueCannotBeEmptyException("The Test value field cannot Be Empty");
        }
        if (testValues == null) {
            throw new TestDefaultValueNotFoundException("No Test value found against the id" + testDefaultValue);
        }
        return testValues;
    }

    @Override
    public void deleteTestDefaultValue(int testDefaultValueID) throws TestDefaultValueNotFoundException, invalidTestValueIDException {
        TestDefaultValueEntity testValue = getTestDefaultValueByID(testDefaultValueID);
        if (testValue == null) {
            throw new TestDefaultValueNotFoundException("No Test value found for deletion.");
        }
        em.remove(testValue);
    }

    @Override
    public void updateTestDefaultValue(int testDefaultValueID, double newTestDefaultValue, boolean isMale, boolean isDeleted)
            throws invalidTestValueIDException, InvalidTestValueException, InvalidGenderException, InvalidDeletedStatusException, TestDefaultValueNotFoundException, TestDefaultValueNotUpdatedException {

        if (testDefaultValueID <= 0) {
            throw new invalidTestValueIDException("ID cannot be zero or negative.");
        }

        if (newTestDefaultValue <= 0) {
            throw new InvalidTestValueException("The test default value must be greater than zero.");
        }

        if (!(isMale == true || isMale == false)) {
            throw new InvalidGenderException("The gender value is invalid.");
        }

        if (!(isDeleted == true || isDeleted == false)) {
            throw new InvalidDeletedStatusException("The deleted status value is invalid.");
        }

        TestDefaultValueEntity testValue = em.find(TestDefaultValueEntity.class, testDefaultValueID);
        if (testValue == null) {
            throw new TestDefaultValueNotFoundException("No Test value found with ID: " + testDefaultValueID);
        }

        boolean isUpdated = false;

        if (newTestDefaultValue > 0) {
            testValue.setTestDefaultValue(newTestDefaultValue);
            isUpdated = true;
        }

        if (isMale == true || isMale == false) {
            testValue.setIsMale(isMale);
            isUpdated = true;
        }

        if (isDeleted == true || isDeleted == false) {
            testValue.setIsDeleted(isDeleted);
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new TestDefaultValueNotUpdatedException("Update Failed! kindly enter atleast one value yto update");
        }

        em.merge(testValue);
    }

    public void persist(Object object) {
        em.persist(object);
    }
}