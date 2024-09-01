/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.userRoleBean;

import com.dreamersoft.hms.entity.UserRoleEntity;
import com.dreamersoft.hms.sessionbeans.roleBean.RoleNotFoundException;
import com.dreamersoft.hms.sessionbeans.userBean.UserNotFoundException;
import java.util.List;

import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface UserRoleManagerRemote {

    public UserRoleEntity getUserRoleByID(int userRoleID) throws UserRoleNotFoundException;

    public List<UserRoleEntity> getAllUserRoleListByUserID(int userID) throws UserNotFoundException, UserRoleNotFoundException;

    public List<UserRoleEntity> getAllUserRoleListByRoleID(int roleID) throws RoleNotFoundException, UserRoleNotFoundException;

    public UserRoleEntity getUserRoleByRoleIDandUserID(int roleID, int userID) throws UserNotFoundException, RoleNotFoundException, UserRoleNotFoundException;

    public List<UserRoleEntity> getAllUserRoleRecord() throws UserRoleNotFoundException;

    public UserRoleEntity addNewUserRole(int roleID, int userID) throws UserRoleAlreadyExistsException, UserNotFoundException, RoleNotFoundException;

    public UserRoleEntity updateUserRoleByID(int userRoleID, int newUserID, int newRoleID) throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException, DuplicateUserRoleException;

    public UserRoleEntity deleteUserRoleByID(int userRoleID) throws UserRoleNotFoundException;

   



 
    
}
