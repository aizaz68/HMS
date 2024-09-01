/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.patientVsitTestBean;

import com.dreamersoft.hms.entity.PatientVisitEntity;
import com.dreamersoft.hms.entity.PatientVisitTestEntity;
import com.dreamersoft.hms.entity.TestEntity;
import com.dreamersoft.hms.sessionbeans.patientVisitBean.PatientVisitManager;
import com.dreamersoft.hms.sessionbeans.patientVisitBean.PatientVisitNotFoundException;
import com.dreamersoft.hms.sessionbeans.testBean.TestManager;
import com.dreamersoft.hms.sessionbeans.testBean.TestNotFoundException;
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
public class PatientVisitTestManager implements PatientVisitTestManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    PatientVisitManager patientVisitManager;
    
    @EJB
    TestManager testManager;
    
     
    
    @Override
     public PatientVisitTestEntity getPatientVisitTestByID(int patientVisitTestID) throws PatientVisitTestNotFoundException{
     Query qry=em.createQuery("Select s From PatientVisitTestEntity s Where s.patientVisitTestId=:  patientVisitTestID and s.isDeleted=false");
             qry.setParameter("patientVisitTestID", patientVisitTestID);
             PatientVisitTestEntity patientVisitTestEntity;

             try{
                 patientVisitTestEntity=(PatientVisitTestEntity) qry.getSingleResult();
                 
             }
             catch(NoResultException ex){
             throw new PatientVisitTestNotFoundException("No Patient Visit Test Found With this PatientVisitTestID: "+patientVisitTestID);
             }
             return patientVisitTestEntity;
             
     }

    @Override
     public PatientVisitTestEntity getPatientVisitTestByTestIDandPatientVisitID(int testID, int patientVisitID) throws TestNotFoundException, PatientVisitTestNotFoundException, PatientVisitNotFoundException{
    TestEntity testEntity =testManager.getTestByID(testID);
    PatientVisitEntity patientVisitEntity =patientVisitManager.getPatientVisitByID(patientVisitID);
    Query qry= em.createQuery("Select s From PatientVisitTestEntity s Where s.testId.testId=:testID and s.patientVisitId.patientVisitId=:patientVisitID and s.isDeleted=false");
    qry.setParameter("testID", testID);
    qry.setParameter("patientVisitID", patientVisitID);
    PatientVisitTestEntity patientVisitTestEntity;
    
    try{
    patientVisitTestEntity=(PatientVisitTestEntity) qry.getSingleResult();
    }
    catch(NoResultException ex){
        throw new PatientVisitTestNotFoundException("No Patient Visit Test Found With this Test ID :"+testID+" and PatientVisitID: "+patientVisitID);
        
    }
    
    return patientVisitTestEntity;
    }

     @Override
    public List<PatientVisitTestEntity> getPatientVisitTestListByPatientVisitID(int patientVisitID) throws PatientVisitTestNotFoundException, PatientVisitNotFoundException{
    List<PatientVisitTestEntity> patientVisitTestEntityList=null;
    PatientVisitEntity patientVisitEntity=patientVisitManager.getPatientVisitByID(patientVisitID);
    Query qry=em.createQuery("Select s From PatientVisitTestEntity s Where s.patientVisitId.patientVisitId=:patientVisitID and s.isDeleted=false");
    qry.setParameter("patientVisitID", patientVisitID);
    patientVisitTestEntityList=qry.getResultList();
    if(patientVisitTestEntityList.isEmpty()){
    throw new PatientVisitTestNotFoundException("No Patient Visit Test Found with this PatientVisitID: "+patientVisitID);
    }
    return patientVisitTestEntityList;
    }

    @Override
    public List<PatientVisitTestEntity> getPatientVisitTestListByTestID(int testID) throws TestNotFoundException, PatientVisitTestNotFoundException{
    TestEntity testEntity =testManager.getTestByID(testID);
    List<PatientVisitTestEntity> patientVisitTestEntityList=null;
    Query qry=em.createQuery("Select s From PatientVisitTestEntity s Where s.testId.testId=:testID and s.isDeleted=false");
    qry.setParameter("testID", testID);
    patientVisitTestEntityList=qry.getResultList();
    if(patientVisitTestEntityList.isEmpty()){
    throw new PatientVisitTestNotFoundException("Patient Visit Test Not Found With this TestID:"+testID);
    }
    return patientVisitTestEntityList;
    }
   
    @Override
    public List<PatientVisitTestEntity> getAllPatientVisitTestList() throws PatientVisitTestNotFoundException{
    List<PatientVisitTestEntity> patientVisitTestEntityList=null;
    Query qry=em.createNamedQuery("PatientVisitTestEntity.findAll ",PatientVisitTestEntity.class);
    
    patientVisitTestEntityList=qry.getResultList();
    if(patientVisitTestEntityList.isEmpty()){
    throw new PatientVisitTestNotFoundException("Patient Visit Test Not Found");
    }
    return patientVisitTestEntityList;
    }
    
    @Override
    public List<PatientVisitTestEntity> getAllActivePatientVisitTest() throws PatientVisitTestNotFoundException{
         List<PatientVisitTestEntity> patientVisitTestEntityList=null;
    Query qry=em.createQuery("Select s From PatientVisitTestEntity s Where  s.isDeleted=false");
    
    patientVisitTestEntityList=qry.getResultList();
    if(patientVisitTestEntityList.isEmpty()){
    throw new PatientVisitTestNotFoundException("Patient Visit Test Not Found");
    }
    return patientVisitTestEntityList;
    
    }

    @Override
    public int countPatientVisitTestByPatientVisitID(int patientVisitID) throws PatientVisitTestNotFoundException{
    Query qry=em.createQuery("Select Count(s) From PatientVisitTestEntity s Where s.patientVisitId.patientVisitId=:patientVisitID and s.isDeleted=false");
    qry.setParameter("patientVisitID", patientVisitID);
    Long count =(Long) qry.getSingleResult();
    if(count<1){
    throw new PatientVisitTestNotFoundException("No Record Found For This Patient Visit ID :"+patientVisitID);
    }
    return count.intValue();
    }

    @Override
    public PatientVisitTestEntity deletePatientVisitTestByID(int patientVisitTestID) throws PatientVisitTestNotFoundException{
    PatientVisitTestEntity patientVisitTestEntity=this.getPatientVisitTestByID(patientVisitTestID);
    patientVisitTestEntity.setIsDeleted(true);
    em.merge(patientVisitTestEntity);
    return patientVisitTestEntity;
    }

    @Override
    public PatientVisitTestEntity restorePatientVisitTestByID(int patientVisitTestID) throws PatientVisitTestNotFoundException{
    Query qry=em.createQuery("Select s From PatientVisitTestEntity s   Where  s.patientVisitTestId=:patientVisitTestID and  s.isDeleted=true");
    qry.setParameter("patientVisitTestID", patientVisitTestID);
        PatientVisitTestEntity patientVisitTestEntity;
    try{
    patientVisitTestEntity=(PatientVisitTestEntity) qry.getSingleResult();
    patientVisitTestEntity.setIsDeleted(false);
    em.merge(patientVisitTestEntity);
    }
    catch(NoResultException ex){
    throw new PatientVisitTestNotFoundException("No Patient Visit Test Found With this Patient Visit TestID:  "+patientVisitTestID);
    }
    return patientVisitTestEntity;
        
        
    }

    
    @Override
    public PatientVisitTestEntity addNewPatientVisitTest(int testID,int patientVisitID) throws TestNotFoundException, PatientVisitNotFoundException, PatientVisitTestNotFoundException, PatientVisitTestAlreadyExistsException{
    
        TestEntity testEntity= testManager.getTestByID(testID);
        PatientVisitEntity patientVisitEntity=patientVisitManager.getPatientVisitByID(patientVisitID);
        Query qry= em.createQuery("Select s From PatientVisitTestEntity s Where s.testId.testId=:testID and s.patientVisitId.patientVisitId=:patientVisitID and s.isDeleted=false");
        qry.setParameter("testID", testID);
        qry.setParameter("patientVistID", patientVisitID);
        PatientVisitTestEntity patientVisitTestEntity=null;
        try{
            patientVisitTestEntity=(PatientVisitTestEntity) qry.getSingleResult();
        if(patientVisitTestEntity!=null){
            throw new PatientVisitTestAlreadyExistsException("Patient Visit Test Alreasy Exists With this testID: "+testID+" and patientVisitID: "+patientVisitID);
            
        }
        }
        catch(NoResultException ex){
        patientVisitTestEntity=new PatientVisitTestEntity();
        patientVisitTestEntity.setPatientVisitId(patientVisitEntity);
        patientVisitTestEntity.setTestId(testEntity);
        em.persist(patientVisitTestEntity);
        
        }

return patientVisitTestEntity;        
    }
    
    
    @Override 
    public PatientVisitTestEntity updatePatientVisitTestByID(int patientVisitTestID,int newTestID,int newPatientVisitID) throws PatientVisitTestNotFoundException, TestNotFoundException, PatientVisitNotFoundException, DuplicatePatientVisitTestException{
    PatientVisitTestEntity patientVisitTestEntity=this.getPatientVisitTestByID(patientVisitTestID);
    TestEntity testEntity= testManager.getTestByID(newTestID);
        PatientVisitEntity patientVisitEntity=patientVisitManager.getPatientVisitByID(newPatientVisitID);
    try{
    PatientVisitTestEntity patientVisitTestEntityUpdate=this.getPatientVisitTestByTestIDandPatientVisitID(newTestID, newPatientVisitID);
    if(!Objects.equals(patientVisitTestEntity.getPatientVisitTestId(), patientVisitTestEntityUpdate.getPatientVisitTestId())){
    throw new DuplicatePatientVisitTestException("Patient Visit Test values are already registered against another record testID: "+newTestID+" and PatientVisitID: "+newPatientVisitID);
    }
    }
    catch(PatientVisitTestNotFoundException ex){
    patientVisitTestEntity.setPatientVisitId(patientVisitEntity);
    patientVisitTestEntity.setTestId(testEntity);
    em.merge(patientVisitTestEntity);
    }
    
    return patientVisitTestEntity;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }
}
