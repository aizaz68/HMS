/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testUnitBean;

import com.dreamersoft.hms.entity.TestUnitEntity;
import com.dreamersoft.hms.sessionbeans.testBean.TestNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface TestUnitManagerRemote {

    public TestUnitEntity getTestUnitByID(int testUnitID) throws TestUnitNotFoundException;

    public List<TestUnitEntity> getTestUnitsLike(String testUnit) throws TestUnitNotFoundException, InvalidTestUnitException;

    public List<TestUnitEntity> getAllTestUnitList() throws TestUnitNotFoundException;

    public List<TestUnitEntity> getAllActiveTestUnits() throws TestUnitNotFoundException;

    public TestUnitEntity deleteTestUnitByID(int testUnitID) throws TestUnitNotFoundException;

    public TestUnitEntity restoreTestUnitByID(int testUnitID) throws TestUnitNotFoundException;

    public TestUnitEntity addNewTestUnit(String testUnit) throws InvalidTestUnitException, TestUnitNotFoundException, TestUnitAlreadyExistsException;

    public TestUnitEntity updateTestUnitByID(int testUnitID, String testUnit) throws TestUnitNotFoundException, InvalidTestUnitException, DuplicateTestUnitException;

    public TestUnitEntity getTestUnitByTestUnit(String testUnit) throws InvalidTestUnitException, TestUnitNotFoundException;

    public int countAllTestUnits() throws TestNotFoundException;

    public int countActiveTestUnits() throws TestNotFoundException;

    public String checkTestUnit(String testUnit) throws InvalidTestUnitException;



}
