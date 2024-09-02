/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.hospitalBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.entity.HospitalEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
    public HospitalEntity getHospitalByID(int hospitalID) throws HospitalNotFoundException {
        HospitalEntity hospital = em.find(HospitalEntity.class, hospitalID);
        if (hospital == null || hospital.getIsDeleted()) {
            throw new HospitalNotFoundException("Hospital of id " + hospitalID + " not found");
        }
        return hospital;
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //get by name
    @Override
    public HospitalEntity getHospitalByName(String hospitalName) throws HospitalNotFoundException {
        try {
            Query qry = em.createNamedQuery("HospitalEntity.findByHospitalName");
            qry.setParameter("hospitalName", hospitalName);

            HospitalEntity hospital = (HospitalEntity) qry.getSingleResult();

            // Check if the hospital is marked as deleted
            if (hospital.getIsDeleted()) {
                throw new HospitalNotFoundException("Hospital with name " + hospitalName + " is deleted");
            }

            return hospital;
        } catch (NoResultException e) {
            throw new HospitalNotFoundException("Hospital with name " + hospitalName + " not found");
        } catch (NonUniqueResultException e) {
            throw new HospitalNotFoundException("Multiple hospitals found with the name " + hospitalName);
        }
    }

    @Override
    public HospitalEntity getHospitalByHospitalNameAndHospitalContactNumber(String hospitalName, String hospitalContactNumber) throws HospitalNotFoundException {
        HospitalEntity hospitalEntity = null;
        try {

            Query qry = em.createQuery("SELECT s FROM HospitalEntity s WHERE s.hospitalName = : hospitalName AND s.hospitalContactNumber = : hospitalContactNumber AND s.isDeleted = flase");
            qry.setParameter("hospitalName", hospitalName);
            qry.setParameter("hospitalContactNumber", hospitalContactNumber);
            hospitalEntity = (HospitalEntity) qry.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new HospitalNotFoundException("Hospital not found with name " + hospitalName + " and contact numbert " + hospitalContactNumber);
        }
        return hospitalEntity;
    }
    //add hospital

    @Override
    public HospitalEntity addHospital(String hospitalName, String hospitalAddress,
            String hospitalContactNumber, String hospitalEmail)
            throws InvalidHospitalAddressException, InvalidHospitalNameException, InvalidContactNumberException, InvalidEmailException, HospitalAlreadyExistException, HospitalNotFoundException {
        if (hospitalName == null || hospitalName.isEmpty()) {
            throw new InvalidHospitalNameException("Hospital name is null or empty");
        } else if (hospitalAddress.isEmpty() || hospitalAddress.isEmpty()) {
            throw new InvalidHospitalAddressException("Hospital Address is null or empty");
        } else if (hospitalContactNumber == null || hospitalContactNumber.isEmpty()) {
            throw new InvalidContactNumberException("Contact Number is null or empty");
        } else if (hospitalEmail == null || hospitalEmail.isEmpty()) {
            throw new InvalidEmailException("Email is null or empty");
        }

        HospitalEntity hospital = getHospitalByHospitalNameAndHospitalContactNumber(hospitalName, hospitalContactNumber);
        if (hospital != null) {
            throw new HospitalAlreadyExistException("Hospital already found with name " + hospitalName + " and contact numbert " + hospitalContactNumber);

        }

        hospital = new HospitalEntity();
        hospital.setHospitalName(hospitalName);
        hospital.setHospitalAddress(hospitalAddress);
        hospital.setHospitalContactNumber(hospitalContactNumber);
        hospital.setHospitalMail(hospitalEmail);
        hospital.setIsDeleted(false);
        em.persist(hospital);
        return hospital;
    }
    //delete hospital by id

    public void deleteHospitalById(int hospitalId) throws HospitalNotFoundException {
        HospitalEntity hospital = getHospitalByID(hospitalId);
        hospital.setIsDeleted(true);
    }

    //delete hospital by name
    public void deleteHospitalByName(String hospitalName) throws HospitalNotFoundException {
        HospitalEntity hospital = getHospitalByName(hospitalName);
        hospital.setIsDeleted(true);
    }

    //update hospital
    @Override
    public void updateHospital(int hospitalId, String newHospitalName, String newHospitalAddress, String newHospitalContactNumber, String newHospitalEmail) throws HospitalNotFoundException, InvalidHospitalAddressException, InvalidContactNumberException, InvalidEmailException, HospitalAlreadyExistException {
        if (newHospitalAddress.isEmpty() || newHospitalAddress.isEmpty()) {
            throw new InvalidHospitalAddressException("Hospital Address is null or empty");
        } else if (newHospitalContactNumber == null || newHospitalContactNumber.isEmpty()) {
            throw new InvalidContactNumberException("Contact Number is null or empty");
        } else if (newHospitalEmail == null || newHospitalEmail.isEmpty()) {
            throw new InvalidEmailException("Email is null or empty");
        }
        
        
        HospitalEntity hospitalEntity = getHospitalByHospitalNameAndHospitalContactNumber(newHospitalName, newHospitalContactNumber);
        if(hospitalEntity != null){
            throw new HospitalAlreadyExistException("Hospital not found with name " + newHospitalName + " and contact numbert " + newHospitalContactNumber);
        }
        
        
        hospitalEntity = getHospitalByID(hospitalId);

        

        hospitalEntity.setHospitalAddress(newHospitalAddress);
        hospitalEntity.setHospitalContactNumber(newHospitalContactNumber);
        hospitalEntity.setHospitalMail(newHospitalEmail);
        hospitalEntity.setIsDeleted(false);

        em.merge(hospitalEntity);
    }
    
    @Override
    public List<DoctorEntity> getHospitalList() throws HospitalNotFoundException{
        Query qry = em.createQuery("SELECT h FROM HospitalEntity h WHERE h.isDeleted = false");
        List<DoctorEntity> doctorList= qry.getResultList();
        if(doctorList.isEmpty()){
            throw new HospitalNotFoundException("No record found in Hospital list");
        }
        return doctorList;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
