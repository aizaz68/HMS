/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testBean;

import com.dreamersoft.hms.entity.TestEntity;
import com.dreamersoft.hms.sessionbeans.testDefaultValueBean.TestDefaultValueNotFoundException;
import com.dreamersoft.hms.sessionbeans.testUnitBean.TestUnitNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface TestManagerRemote {

    public TestEntity getTestByID(int testID) throws TestNotFoundException;

    public TestEntity getTestByTestNameAndTestUnitIDandTestDefaultValue(String testName, int testUnitID, int testDefaultValueID) throws TestUnitNotFoundException, TestDefaultValueNotFoundException, TestNotFoundException, InvalidTestNameExcepiton;

    public List<TestEntity> getTestListByNameLike(String testName) throws InvalidTestNameExcepiton, TestNotFoundException;

    public List<TestEntity> getTestListByTestUnitID(int testUnitID) throws TestUnitNotFoundException, TestNotFoundException;

    public List<TestEntity> getTestListByTestDefaultValueID(int testDefaultValueID) throws TestDefaultValueNotFoundException, TestNotFoundException;

    public List<TestEntity> getAllTestList() throws TestNotFoundException;

    public List<TestEntity> getAllActiveTestList() throws TestNotFoundException;

    public int countAllActiveTest();

    public int countTotalTest();

    public int countNotActiveTest();

    public TestEntity deleteTestByID(int testID) throws TestNotFoundException;

    public TestEntity restoreTestByID(int testID) throws TestNotFoundException;

    public TestEntity addNewTest(String testName, int testUnitID, int testDefaultValueID) throws TestUnitNotFoundException, TestDefaultValueNotFoundException, TestNotFoundException, InvalidTestNameExcepiton, TestAlreadyExistsException;

    public TestEntity updateTestByID(int testID, String newTestName, int newTestUnitID, int newTestDefaultValueID) throws TestNotFoundException, TestUnitNotFoundException, TestDefaultValueNotFoundException, InvalidTestNameExcepiton, DuplicateTestException;

    public String checkTestName(String testName) throws InvalidTestNameExcepiton;


    
}
