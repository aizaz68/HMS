/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testUnitBean;

import com.dreamersoft.hms.entity.TestUnitEntity;
import com.dreamersoft.hms.sessionbeans.testBean.TestNotFoundException;
import java.util.List;
import java.util.Objects;
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
public class TestUnitManager implements TestUnitManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @Override
    public TestUnitEntity getTestUnitByID(int testUnitID) throws TestUnitNotFoundException {
        TestUnitEntity testUnitEntity;
        Query qry = em.createQuery("Select s From TestUnitEntity s Where s.testUnitId=:testUnitID and s.isDeleted=false");
        qry.setParameter("testUnitID", testUnitID);
        try {
            testUnitEntity = (TestUnitEntity) qry.getSingleResult();

        } catch (NoResultException ex) {
            throw new TestUnitNotFoundException("Test Unit Not Found with this TestID: " + testUnitID);
        }
        return testUnitEntity;

    }

    @Override
    public List<TestUnitEntity> getTestUnitsLike(String testUnit) throws TestUnitNotFoundException, InvalidTestUnitException {

        this.checkTestUnit(testUnit);
        List<TestUnitEntity> testUnitEntityList = null;
        Query qry = em.createQuery("Select s From TestUnitEntity s Where s.testUnit LIKE (:testUnit) and s.isDeleted=false");
        qry.setParameter("testUnit", "%" + testUnit + "%");
        testUnitEntityList = (List<TestUnitEntity>) qry.getResultList();
        if (testUnitEntityList.isEmpty()) {
            throw new TestUnitNotFoundException("No Likely Test Unit Found with this TestUnit: " + testUnit);

        }

        return testUnitEntityList;
    }

    @Override
    public List<TestUnitEntity> getAllTestUnitList() throws TestUnitNotFoundException {
        Query qry = em.createNamedQuery("TestUnitEntity.findAll", TestUnitEntity.class);
        List<TestUnitEntity> testUnitEntityList = qry.getResultList();
        if (testUnitEntityList.isEmpty()) {
            throw new TestUnitNotFoundException(" Test Unit Record Not Found ");

        }
        return testUnitEntityList;
    }

    
    @Override
    public List<TestUnitEntity> getAllActiveTestUnits() throws TestUnitNotFoundException{
      Query qry=em.createQuery("Select s From TestUnitEntity s where s.isDeleted=false");
      List<TestUnitEntity>  testUnitEntityList=qry.getResultList();
      if(testUnitEntityList.isEmpty()){
      throw new TestUnitNotFoundException("No Active Test Unit Found ");
      }
      return testUnitEntityList;
    }

    @Override
    public TestUnitEntity deleteTestUnitByID(int testUnitID) throws TestUnitNotFoundException{
    TestUnitEntity testUnitEntity=this.getTestUnitByID(testUnitID);
    testUnitEntity.setIsDeleted(true);
    em.merge(testUnitEntity);
    return testUnitEntity;
    }

    @Override
    public TestUnitEntity restoreTestUnitByID(int testUnitID) throws TestUnitNotFoundException{
    Query qry=em.createNamedQuery("TestUnitEntity.findByTestUnitId",TestUnitEntity.class);
    qry.setParameter("testUnitID", testUnitID);
    TestUnitEntity testUnitEntity;
    try{
    testUnitEntity =(TestUnitEntity) qry.getSingleResult();
    testUnitEntity.setIsDeleted(false);
    em.merge(testUnitEntity);
    
    }catch(NoResultException ex){
    throw new TestUnitNotFoundException("No Test Unit Found With this testUnitID: "+testUnitID);
    }
    return testUnitEntity;
    
    }
    
    @Override
    public TestUnitEntity addNewTestUnit(String testUnit) throws InvalidTestUnitException, TestUnitNotFoundException, TestUnitAlreadyExistsException{
     TestUnitEntity testUnitEntity=null;
        try{
       testUnitEntity= this.getTestUnitByTestUnit(testUnit);
        if(testUnitEntity!=null){
        throw new TestUnitAlreadyExistsException("TestUnit Already Exists  testUnit: "+testUnit);
        }
        
        }catch(TestUnitNotFoundException ex){
        testUnitEntity=new TestUnitEntity();
        testUnitEntity.setTestUnit(testUnit);
        em.persist(testUnitEntity);
        }
        
        return testUnitEntity;
    }

    @Override
    public TestUnitEntity updateTestUnitByID(int testUnitID, String testUnit) throws TestUnitNotFoundException, InvalidTestUnitException, DuplicateTestUnitException{
         TestUnitEntity testUnitEntity= this.getTestUnitByID(testUnitID);
         try{
         TestUnitEntity testUnitUpdate=this.getTestUnitByTestUnit(testUnit);
         if(!Objects.equals(testUnitEntity.getTestUnitId(), testUnitUpdate.getTestUnitId())){
         throw new DuplicateTestUnitException("Test Unit is Already Resgisted Against another Record TestUnit: "+testUnit);
         }
         }catch(TestUnitNotFoundException ex){
         testUnitEntity.setTestUnit(testUnit);
         em.merge(testUnitEntity);
         }
         return testUnitEntity;
    }

    
    @Override
    public TestUnitEntity getTestUnitByTestUnit(String testUnit) throws InvalidTestUnitException, TestUnitNotFoundException{
     this.checkTestUnit(testUnit);
     Query qry= em.createQuery("Select s From TestUnitEntity  s Where s.testUnit=:testUnit and s.isDeleted=false");
     qry.setParameter("testUnit", testUnit);
     TestUnitEntity testUnitEntity;
     
     try{
     testUnitEntity=(TestUnitEntity) qry.getSingleResult();
     }
     catch(NoResultException ex){
     throw new TestUnitNotFoundException("No Test Unit Found with this TestUnit: "+testUnit);
     }
     return testUnitEntity;
    }

    
    @Override
    public int countAllTestUnits() throws TestNotFoundException{
    Query qry=em.createQuery("Select Count(s) From TestEntity s ");
    Long count = (Long) qry.getSingleResult();
    if(count<1){
    throw new TestNotFoundException("No Test Unit Record Found");
    }
    return count.intValue();
            
    
    }

    @Override
    public int countActiveTestUnits() throws TestNotFoundException{
    Query qry=em.createQuery("Select Count(s) From TestEntity s  Where s.isDeleted=false");
    Long count = (Long) qry.getSingleResult();
    if(count<1){
    throw new TestNotFoundException("No Active  Test Unit Record Found");
    }
    return count.intValue();
            
    }

    @Override
    public String checkTestUnit(String testUnit) throws InvalidTestUnitException {
        if (testUnit.isEmpty() || testUnit.length() < 1) {
            throw new InvalidTestUnitException("Invalid value for TestUnit: " + testUnit);
        }
        return testUnit;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        em.persist(object);
    }
}
