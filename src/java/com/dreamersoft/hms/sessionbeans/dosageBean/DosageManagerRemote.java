/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.dosageBean;

import com.dreamersoft.hms.entity.DosageEntity;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface DosageManagerRemote {

    public void deleteDosageById(int dosageID) throws DosageNotFoundException;

    public DosageEntity updateDosage(int dosageID, String dosageDescription) throws DosageNotFoundException;

    public DosageEntity addNewDosage(String dosageDescription) throws DuplicateDosageException;

    public DosageEntity getDosageByID(int dosageID) throws DosageNotFoundException;

    public void restoreDeletedDosage(int dosageId) throws DosageNotFoundException;

    public List<DosageEntity> getAllDosages() throws DosageNotFoundException;
    
  
    
}
