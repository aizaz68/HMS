

   /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.dosageBean;

import com.dreamersoft.hms.entity.DosageEntity;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Grimoire
 */
@Stateless
public class DosageManager implements DosageManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

  
    @Override
    public DosageEntity getDosageByID(int dosageID) throws DosageNotFoundException {
        DosageEntity dosageEntity;
        Query qry = em.createQuery("select d from DosageEntity d where d.dosageId=:dosageID and d.isDeleted = false");
        qry.setParameter("dosageID", dosageID);
        try {
            dosageEntity = (DosageEntity) qry.getSingleResult();
        } catch (NoResultException ex) {
            throw new DosageNotFoundException("Dosage Not Found For This ID: " + dosageID);
        }

        return dosageEntity;
    }

    @Override
    public List<DosageEntity> getAllDosages() throws DosageNotFoundException {
        Query qry = em.createQuery("select d from DosageEntity d where d.isDeleted = false");
        List<DosageEntity> dosageList = qry.getResultList();
        if(dosageList.isEmpty()){
            throw new DosageNotFoundException("No dosage found");
        }
        return dosageList;
    }

    @Override
    public DosageEntity addNewDosage(String dosageDescription) throws DuplicateDosageException {
        DosageEntity dosageEntity = new DosageEntity();
        dosageEntity.setDosageDescription(dosageDescription);
        dosageEntity.setIsDeleted(false);

        em.persist(dosageEntity);
        return dosageEntity;
    }

    @Override
    public DosageEntity updateDosage(int dosageID, String dosageDescription) throws DosageNotFoundException {
        DosageEntity dosageEntity = this.getDosageByID(dosageID);
        dosageEntity.setDosageDescription(dosageDescription);
        em.merge(dosageEntity);
        return dosageEntity;
    }

    @Override
    public void deleteDosageById(int dosageID) throws DosageNotFoundException {
        DosageEntity dosageEntity = this.getDosageByID(dosageID);
        dosageEntity.setIsDeleted(true);
        em.merge(dosageEntity);
    }
    @Override
      public  void  restoreDeletedDosage(int dosageId) throws DosageNotFoundException{
        DosageEntity dosageEntity ;
           Query qry     = em.createQuery("select d from DosageEntity d where d.dosageId=:dosageId and d.isDeleted = false");
           qry.setParameter("dosageId", dosageId);
           dosageEntity =(DosageEntity) qry.getSingleResult();
        if (dosageEntity != null) {
            dosageEntity.setIsDeleted(false);
            em.merge(dosageEntity);
        } else {
            throw new DosageNotFoundException("No dosage  found for id: " + dosageId);
        
        }
     
      }
       
    

 public void persist(Object object) {
        em.persist(object);
    }


}
