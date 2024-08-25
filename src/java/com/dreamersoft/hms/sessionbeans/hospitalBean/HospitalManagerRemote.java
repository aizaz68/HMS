/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.hospitalBean;

import com.dreamersoft.hms.entity.HospitalEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface HospitalManagerRemote {

    public HospitalEntity getHospitalByID(int hospitalID) throws HospitalNotFoundException;

    public HospitalEntity getHospitalByName(String hospitalName) throws HospitalNotFoundException;

    public void addHospital(String hospitalName, String hospitalAddress, String hospitalContactNumber, String hospitalEmail) throws InvalidHospitalAddressException, InvalidHospitalNameException, InvalidContactNumberException, InvalidEmailException;

    public void deleteHospitalById(int hospitalId) throws HospitalNotFoundException;

    public void deleteHospitalByName(String hospitalName) throws HospitalNotFoundException;
    
    public void updateHospital(String hospitalName, String hospitalAddress, String hospitalContactNumber, String hospitalEmail) throws HospitalNotFoundException, InvalidHospitalAddressException, InvalidContactNumberException, InvalidEmailException;


    
}
