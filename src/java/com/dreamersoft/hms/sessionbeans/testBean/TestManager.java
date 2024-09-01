/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testBean;

import com.dreamersoft.hms.entity.TestDefaultValueEntity;
import com.dreamersoft.hms.entity.TestEntity;
import com.dreamersoft.hms.entity.TestUnitEntity;
import com.dreamersoft.hms.sessionbeans.testDefaultValueBean.TestDefaultValueManagerRemote;
import com.dreamersoft.hms.sessionbeans.testDefaultValueBean.TestDefaultValueNotFoundException;
import com.dreamersoft.hms.sessionbeans.testUnitBean.TestUnitManagerRemote;
import com.dreamersoft.hms.sessionbeans.testUnitBean.TestUnitNotFoundException;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aizaz
 */
@Stateless
public class TestManager implements TestManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    TestUnitManagerRemote testUnitManager;

    @EJB
    TestDefaultValueManagerRemote testDefaultValueManager;

    @Override
    public TestEntity getTestByID(int testID) throws TestNotFoundException {
        TestEntity testEntity;
        Query qry = em.createQuery("Select s From TestEntity s Where s.testId=:testID and s.isDeleted=false");
        qry.setParameter("testID", testID);
        try {

            testEntity = (TestEntity) qry.getSingleResult();
        } catch (NoResultException ex) {
            throw new TestNotFoundException("No Test Found with this testID:" + testID);
        }
        return testEntity;
    }

    @Override
    public TestEntity getTestByTestNameAndTestUnitIDandTestDefaultValue(String testName, int testUnitID, int testDefaultValueID) throws TestUnitNotFoundException, TestDefaultValueNotFoundException, TestNotFoundException, InvalidTestNameExcepiton {
        TestUnitEntity testUnitEntity = testUnitManager.getTestUnitByID(testUnitID);
        TestDefaultValueEntity testDefaultValueEntity = testDefaultValueManager.getTestDefaultValueByID(testDefaultValueID);
        this.checkTestName(testName);
        Query qry = em.createQuery("Select s From TestEntity s Where s.testName=:testName and s.testUnitId.testUnitId=:testUnitID and s.testDefaultValueId.testDefaultValueId=:testDefaultValueID and s.isDeleted=false");
        qry.setParameter("testName", testName);
        qry.setParameter("testUnitID", testUnitID);
        qry.setParameter("testDefaultValueID", testDefaultValueID);
        TestEntity testEntity;

        try {
            testEntity = (TestEntity) qry.getSingleResult();
        } catch (NoResultException ex) {
            throw new TestNotFoundException("No test Found with this testName: " + testName + " and testUnitID: " + testUnitID + " and testDefaultValueID: " + testDefaultValueID);
        }
        return testEntity;

    }

    @Override
    public List<TestEntity> getTestListByNameLike(String testName) throws InvalidTestNameExcepiton, TestNotFoundException {
        this.checkTestName(testName);
        Query qry = em.createQuery("Select s From TestEntity s Where Lower(s.testName) LIKE Lower(:testName) and s.isDeleted=false", TestEntity.class);
        qry.setParameter("testName", "%" + testName.toLowerCase() + "%");
        List<TestEntity> testEntityList = qry.getResultList();

        if (testEntityList.isEmpty()) {
            throw new TestNotFoundException("No Test Found that matches the TestName=: " + testName);
        }
        return testEntityList;

    }

    @Override
    public List<TestEntity> getTestListByTestUnitID(int testUnitID) throws TestUnitNotFoundException, TestNotFoundException {
        TestUnitEntity testUnitEntity = testUnitManager.getTestUnitByID(testUnitID);
        Query qry = em.createQuery("Select s From TestEntity s Where s.testUnitId.testUnitId=:testUnitID and s.isDeleted=false", TestEntity.class);
        qry.setParameter("testUnitID", testUnitID);
        List<TestEntity> testEntityList = qry.getResultList();
        if (testEntityList.isEmpty()) {
            throw new TestNotFoundException("Test Not Found Against this testUnitID: " + testUnitID);
        }
        return testEntityList;

    }

    @Override
    public List<TestEntity> getTestListByTestDefaultValueID(int testDefaultValueID) throws TestDefaultValueNotFoundException, TestNotFoundException {
        TestDefaultValueEntity testDefaultValueEntity = testDefaultValueManager.getTestDefaultValueByID(testDefaultValueID);
        Query qry = em.createQuery("Select s From TestEntity s Where s.testDefaultValueId.testDefaultValueId =:testUnitID and s.isDeleted=false", TestEntity.class);
        qry.setParameter("testDefaultValueID", testDefaultValueID);
        List<TestEntity> testEntityList = qry.getResultList();
        if (testEntityList.isEmpty()) {
            throw new TestNotFoundException("Test Not Found Against this testDefaultValueID: " + testDefaultValueID);
        }
        return testEntityList;
    }

    @Override
    public List<TestEntity> getAllTestList() throws TestNotFoundException {
        Query qry = em.createNamedQuery("TestEntity.findAll", TestEntity.class);
        List<TestEntity> testEntityList = qry.getResultList();
        if (testEntityList.isEmpty()) {
            throw new TestNotFoundException("Test Record Not Found  ");
        }
        return testEntityList;
    }

    @Override
    public List<TestEntity> getAllActiveTestList() throws TestNotFoundException {
        Query qry = em.createQuery("Select s From TestEntity s Where s.isDeleted=false", TestEntity.class);
        List<TestEntity> testEntityList = qry.getResultList();
        if (testEntityList.isEmpty()) {
            throw new TestNotFoundException("No Active Test Record Found");
        }
        return testEntityList;

    }

    @Override
    public int countAllActiveTest() {
        Query qry = em.createQuery("Select Count(s) From TestEntity s Where s.isDeleted=false", TestEntity.class);
        Long count = (Long) qry.getSingleResult();
        return count.intValue();
    }

    @Override
    public int countTotalTest() {
        Query qry = em.createQuery("Select COUNT(s) From TestEntity s");
        Long count = (Long) qry.getSingleResult();
        return count.intValue();
    }

    @Override
    public int countNotActiveTest() {
        Query qry = em.createQuery("Select COUNT(s) from TestEntity s Where s.isDeleted=true");
        Long count = (Long) qry.getSingleResult();
        return count.intValue();
    }

    @Override
    public TestEntity deleteTestByID(int testID) throws TestNotFoundException {
        TestEntity testEntity = this.getTestByID(testID);
        testEntity.setIsDeleted(true);
        em.merge(testEntity);

        return testEntity;
    }

    @Override
    public TestEntity restoreTestByID(int testID) throws TestNotFoundException {
        Query qry = em.createQuery("Select s From TestEntity s Where s.isDeleted=:true");
        TestEntity testEntity;
        try {
            testEntity = (TestEntity) qry.getSingleResult();
            testEntity.setIsDeleted(false);
            em.merge(testEntity);

        } catch (NoResultException ex) {
            throw new TestNotFoundException("No Test Found With this Test ID: " + testID);
        }
        return testEntity;

    }

    @Override
    public TestEntity addNewTest(String testName, int testUnitID, int testDefaultValueID) throws TestUnitNotFoundException, TestDefaultValueNotFoundException, TestNotFoundException, InvalidTestNameExcepiton, TestAlreadyExistsException {
        TestEntity testEntity = null;
        try {
            testEntity = this.getTestByTestNameAndTestUnitIDandTestDefaultValue(testName, testUnitID, testDefaultValueID);
            if (testEntity != null) {
                throw new TestAlreadyExistsException("Test Already Exists With this TestName: " + testName + " and testUnitID: " + testUnitID + " and testDefaultValueID: " + testDefaultValueID);
            }
        } catch (TestNotFoundException ex) {

            TestDefaultValueEntity testDefaultValueEntity = testDefaultValueManager.getTestDefaultValueByID(testDefaultValueID);
            TestUnitEntity testUnitEntity = testUnitManager.getTestUnitByID(testUnitID);
            testEntity = new TestEntity();
            testEntity.setTestDefaultValueId(testDefaultValueEntity);
            testEntity.setTestUnitId(testUnitEntity);
            testEntity.setTestName(testName);
            em.persist(testEntity);
        }
        return testEntity;
    }

    @Override
    public TestEntity updateTestByID(int testID, String newTestName, int newTestUnitID, int newTestDefaultValueID) throws TestNotFoundException, TestUnitNotFoundException, TestDefaultValueNotFoundException, InvalidTestNameExcepiton, DuplicateTestException {
        TestEntity testEntity = this.getTestByID(testID);

        try {
            TestEntity testEntityUpdate = this.getTestByTestNameAndTestUnitIDandTestDefaultValue(newTestName, newTestUnitID, newTestDefaultValueID);
            if (!Objects.equals(testEntity.getTestId(), testEntityUpdate.getTestId())) {
                throw new DuplicateTestException("Test values Already registered Against another Record Testname: " + newTestName + " and testUnitID: " + newTestUnitID + " and testDefaultValueID: " + newTestDefaultValueID);
            }
        } catch (TestNotFoundException ex) {
            TestDefaultValueEntity testDefaultValueEntity = testDefaultValueManager.getTestDefaultValueByID(newTestDefaultValueID);
            TestUnitEntity testUnitEntity = testUnitManager.getTestUnitByID(newTestUnitID);

            testEntity.setTestName(newTestName);
            testEntity.setTestUnitId(testUnitEntity);
            testEntity.setTestDefaultValueId(testDefaultValueEntity);
            em.merge(testEntity);
        }

        return testEntity;

    }

    @Override
    public String checkTestName(String testName) throws InvalidTestNameExcepiton {
        if (testName.isEmpty() || testName.length() < 1) {
            throw new InvalidTestNameExcepiton("Invalid value for TestName:" + testName);
        }
        return testName;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }
}
