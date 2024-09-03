/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testDefaultValueBean;

import com.dreamersoft.hms.entity.TestDefaultValueEntity;
import com.dreamersoft.hms.sessionbeans.medicineBean.InvalidDeletedStatusException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface TestDefaultValueManagerRemote {

    public TestDefaultValueEntity AddTestDefaultValue(double testDefaultValue, boolean isMale, boolean isDeleted) throws InvalidTestValueException, InvalidGenderException, InvalidDeletedStatusException;

    public TestDefaultValueEntity getTestDefaultValueByID(int testDefaultValueID) throws invalidTestValueIDException, TestDefaultValueNotFoundException;

    public List<TestDefaultValueEntity> getTestDefaultValueByValue(String testDefaultValue) throws testDefaultValueCannotBeEmptyException, TestDefaultValueNotFoundException;

    public void deleteTestDefaultValue(int testDefaultValueID) throws TestDefaultValueNotFoundException, invalidTestValueIDException;

    public void updateTestDefaultValue(int testDefaultValueID, double newTestDefaultValue, boolean isMale, boolean isDeleted) throws invalidTestValueIDException, InvalidTestValueException, InvalidGenderException, InvalidDeletedStatusException, TestDefaultValueNotFoundException, TestDefaultValueNotUpdatedException;


    
}
