/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.hospitalBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.HospitalEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface HospitalManagerRemote {

    public HospitalEntity getHospitalByName(String hospitalName) throws HospitalNotFoundException;

    public HospitalEntity getHospitalByID(int hospitalID) throws HospitalNotFoundException;

    public HospitalEntity addHospital(String hospitalName, String hospitalAddress, String hospitalContactNumber, String hospitalEmail)
            throws InvalidHospitalAddressException, InvalidHospitalNameException, InvalidContactNumberException, InvalidEmailException, HospitalAlreadyExistException, HospitalNotFoundException;

    public HospitalEntity getHospitalByHospitalNameAndHospitalContactNumber(String hospitalName, String hospitalContactNumber) throws HospitalNotFoundException;

    public void updateHospital(int hospitalId, String newHospitalName, String newHospitalAddress, String newHospitalContactNumber, String newHospitalEmail) throws HospitalNotFoundException, InvalidHospitalAddressException, InvalidContactNumberException, InvalidEmailException, HospitalAlreadyExistException;

    public List<DoctorEntity> getHospitalList() throws HospitalNotFoundException;


    

    
}
