            /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.doctorBean;

import com.dreamersoft.hms.entity.DoctorEntity;
import com.dreamersoft.hms.sessionbeans.hospitalBean.HospitalNotFoundException;
import com.dreamersoft.hms.sessionbeans.userBean.UserNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface DoctorManagerRemote {

    public DoctorEntity getDoctorByID(int doctorID) throws DoctorNotFoundException;

    public DoctorEntity getDoctorByUserIDandDoctorCNICandHospitalID(int userID, String doctorCNIC, int hospitalID) throws DoctorNotFoundException, UserNotFoundException, HospitalNotFoundException, InvalidDoctorCNICException;

    public DoctorEntity addNewDoctor(int userID, String doctorCNIC, int hospitalID) throws DoctorNotFoundException, UserNotFoundException, HospitalNotFoundException, InvalidDoctorCNICException, DoctorAlreayExistException, DuplicateCNICException;

    public void deleteDoctorById(int doctorID) throws DoctorNotFoundException;

    public DoctorEntity updateDoctor(int doctorID, String doctorCNIC, int hospitalID, int userID) throws DoctorNotFoundException, InvalidDoctorCNICException, UserNotFoundException, HospitalNotFoundException, DuplicateDoctorException, DuplicateCNICException;

    public boolean validateCNIC(String doctorCNIC);

    public List<DoctorEntity> getDoctorListByCNIC(String doctorCNIC) throws DoctorNotFoundException, InvalidDoctorCNICException;

   

    public boolean cnicExist(String doctorCNIC);

          
}
