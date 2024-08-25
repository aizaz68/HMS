/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.hospitalBean;

import com.dreamersoft.hms.entity.HospitalEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aizaz
 */
@Stateless
public class HospitalManager implements HospitalManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    //get by id
    @Override
    public HospitalEntity getHospitalByID(int hospitalID) throws HospitalNotFoundException{
        HospitalEntity hospital = em.find(HospitalEntity.class, hospitalID);
        if(hospital == null || hospital.getIsDeleted()){
            throw new HospitalNotFoundException("Hospital of id "+hospitalID+" not found");
        }
        return hospital;
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    //get by name
    @Override
    public HospitalEntity getHospitalByName(String hospitalName) throws HospitalNotFoundException{
        HospitalEntity hospital;
        Query qry = em.createNamedQuery("HospitalEntity.findByHospitalName");
        qry.setParameter("hospitalName", hospitalName);
        hospital = (HospitalEntity) qry.getSingleResult();
        if(hospital == null || hospital.getIsDeleted()){
            throw new HospitalNotFoundException("Hospital with name "+hospitalName+" not found");
        }
        return hospital;
    }
    
    
    //add hospital
    public void addHospital(String hospitalName, String hospitalAddress, String hospitalContactNumber, String hospitalEmail)
    throws InvalidHospitalAddressException, InvalidHospitalNameException, InvalidContactNumberException, InvalidEmailException{
        if(hospitalName == null || hospitalName.isEmpty()){
            throw new InvalidHospitalNameException("Hospital name is null or empty");
        }else if(hospitalAddress.isEmpty() || hospitalAddress.isEmpty()){
            throw new InvalidHospitalAddressException("Hospital Address is null or empty");
        }else if(hospitalContactNumber == null || hospitalContactNumber.isEmpty()){
            throw new InvalidContactNumberException("Contact Number is null or empty");
        }else if(hospitalEmail == null || hospitalEmail.isEmpty()){
            throw new InvalidEmailException("Email is null or empty");
        }
        
        HospitalEntity hospital = new HospitalEntity();
        hospital.setHospitalName(hospitalName);
        hospital.setHospitalAddress(hospitalAddress);
        hospital.setHospitalContactNumber(hospitalContactNumber);
        hospital.setHospitalMail(hospitalEmail);
        hospital.setIsDeleted(false);
    }
    
    
    //delete hospital by id
    public void deleteHospitalById(int hospitalId) throws HospitalNotFoundException{
        HospitalEntity hospital = getHospitalByID(hospitalId);
        hospital.setIsDeleted(true);
    }
    
    
    //delete hospital by name
    public void deleteHospitalByName(String hospitalName) throws HospitalNotFoundException{
        HospitalEntity hospital = getHospitalByName(hospitalName);
        hospital.setIsDeleted(true);
    }
    
    
    //update hospital
    @Override
    public void updateHospital(String hospitalName, String hospitalAddress, String hospitalContactNumber, String hospitalEmail) throws HospitalNotFoundException, InvalidHospitalAddressException, InvalidContactNumberException, InvalidEmailException{
        HospitalEntity hospital;
        hospital = getHospitalByName(hospitalName);
        
        if(hospitalAddress.isEmpty() || hospitalAddress.isEmpty()){
            throw new InvalidHospitalAddressException("Hospital Address is null or empty");
        }else if(hospitalContactNumber == null || hospitalContactNumber.isEmpty()){
            throw new InvalidContactNumberException("Contact Number is null or empty");
        }else if(hospitalEmail == null || hospitalEmail.isEmpty()  ){
            throw new InvalidEmailException("Email is null or empty");
        }
        
        hospital.setHospitalAddress(hospitalAddress);
        hospital.setHospitalContactNumber(hospitalContactNumber);
        hospital.setHospitalMail(hospitalEmail);
        hospital.setIsDeleted(false);
        
        em.merge(hospital);
    }


    public void persist(Object object) {
        em.persist(object);
    }
}
