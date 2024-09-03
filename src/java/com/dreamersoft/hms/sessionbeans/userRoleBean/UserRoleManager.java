/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.userRoleBean;

import com.dreamersoft.hms.entity.RoleEntity;
import com.dreamersoft.hms.entity.UserRoleEntity;
import com.dreamersoft.hms.entity.UsersEntity;
import com.dreamersoft.hms.sessionbeans.roleBean.RoleMangerRemote;
import com.dreamersoft.hms.sessionbeans.roleBean.RoleNotFoundException;
import com.dreamersoft.hms.sessionbeans.userBean.UserManagerRemote;
import com.dreamersoft.hms.sessionbeans.userBean.UserNotFoundException;
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
public class UserRoleManager implements UserRoleManagerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    @EJB
    UserManagerRemote userManager;

    @EJB
    RoleMangerRemote roleManager;

    @Override
    public UserRoleEntity getUserRoleByID(int userRoleID) throws UserRoleNotFoundException {
        Query qry = em.createQuery("Select s from UserRoleEntity s Where s.userRoleId=:userRoleID and s.isDeleted=false");
        qry.setParameter("userRoleID", userRoleID);
        UserRoleEntity userRoleEntity;
        try {
            userRoleEntity = (UserRoleEntity) qry.getSingleResult();
        } catch (NoResultException ex) {
            throw new UserRoleNotFoundException("User Role Not Found with this User role ID : " + userRoleID);

        }
        return userRoleEntity;

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<UserRoleEntity> getAllUserRoleListByUserID(int userID) throws UserNotFoundException, UserRoleNotFoundException {
        List<UserRoleEntity> userRoleEntityList = null;
        UsersEntity userEntity = userManager.getUserByID(userID);
        Query qry = em.createQuery("Select s From UserRoleEntity s Where s.userId.userId=:userID and s.isDeleted=false");
        qry.setParameter("userID", userID);
        userRoleEntityList = (List<UserRoleEntity>) qry.getResultList();
        if (userRoleEntityList.isEmpty()) {

            throw new UserRoleNotFoundException("No User Role  Found  Against this User  ID:  " + userID);
        }

        return userRoleEntityList;

    }

    @Override
    public List<UserRoleEntity> getAllUserRoleListByRoleID(int roleID) throws RoleNotFoundException, UserRoleNotFoundException {
        List<UserRoleEntity> userRoleEntityList = null;
        RoleEntity roleEntity = roleManager.getRoleByID(roleID);
        Query qry = em.createQuery("Select s From UserRoleEntity s Where s.roleId.roleId=:roleID and s.isDeleted=false");
        qry.setParameter("roleID", roleID);
        userRoleEntityList = (List<UserRoleEntity>) qry.getResultList();
        if (userRoleEntityList.isEmpty()) {

            throw new UserRoleNotFoundException("No User role Found  Against this User Role ID:  " + roleID);
        }

        return userRoleEntityList;

    }

    @Override
    public UserRoleEntity getUserRoleByRoleIDandUserID(int roleID, int userID) throws UserNotFoundException, RoleNotFoundException, UserRoleNotFoundException {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        RoleEntity roleEntity = roleManager.getRoleByID(roleID);
        UsersEntity usersEntity = userManager.getUserByID(userID);

        Query qry = em.createQuery("Select s From UserRoleEntity s Where s.roleId.roleId=:roleID and s.userId.userId=:userID and s.isDeleted=false");
        qry.setParameter("roleID", roleID);
        qry.setParameter("userID", userID);
        try {

            userRoleEntity = (UserRoleEntity) qry.getSingleResult();

        } catch (NoResultException ex) {
            throw new UserRoleNotFoundException("User Role Not Found with this user id :" + userID + " and role Id: " + roleID);
        }
        return userRoleEntity;

    }

    @Override
    public List<UserRoleEntity> getAllUserRoleRecord() throws UserRoleNotFoundException {
        List<UserRoleEntity> userRoleEntityList = null;
        Query qry = em.createQuery("Select s From UserRoleEntity s where s.isDeleted=false");
        userRoleEntityList = (List<UserRoleEntity>) qry.getResultList();
        if (userRoleEntityList.isEmpty()) {
            throw new UserRoleNotFoundException("No User Role Record Found ");
        }
        return userRoleEntityList;

    }

    @Override
    public UserRoleEntity addNewUserRole(int roleID, int userID) throws UserRoleAlreadyExistsException, UserNotFoundException, RoleNotFoundException {
        UserRoleEntity userRoleEntity = null;

        RoleEntity roleEntity = roleManager.getRoleByID(roleID);
        UsersEntity usersEntity = userManager.getUserByID(userID);

        try {
            userRoleEntity = this.getUserRoleByRoleIDandUserID(roleID, userID);
            if (userRoleEntity != null) {
                throw new UserRoleAlreadyExistsException("User Role ALready exists with this user ID:" + userID + " and Role ID:" + roleID);
            }

        } catch (UserRoleNotFoundException ex) {

            userRoleEntity = new UserRoleEntity();
            userRoleEntity.setRoleId(roleEntity);
            userRoleEntity.setUserId(usersEntity);
            em.persist(userRoleEntity);

        }
        
        return userRoleEntity;

    }

    @Override
    public UserRoleEntity updateUserRoleByID(int userRoleID, int newUserID, int newRoleID) throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException, DuplicateUserRoleException {
    UserRoleEntity userRoleEntity=this.getUserRoleByID(userRoleID);
    RoleEntity roleEntity = roleManager.getRoleByID(newRoleID);
    UsersEntity usersEntity = userManager.getUserByID(newUserID);
    UserRoleEntity userRoleEntityCheck;
    try{
    userRoleEntityCheck=this.getUserRoleByRoleIDandUserID(newRoleID, newUserID);
    if(!Objects.equals(userRoleEntity.getUserRoleId(), userRoleEntityCheck.getUserRoleId())){
    throw new DuplicateUserRoleException("User role with this User id:"+newUserID+" and role id :"+newRoleID+"already exists for another record ");
    
    }
    
    
    }catch(UserRoleNotFoundException ex){
            userRoleEntity.setUserRoleId(userRoleID);
            userRoleEntity.setRoleId(roleEntity);
            userRoleEntity.setUserId(usersEntity);
            em.merge(userRoleEntity);
    
    }
    return userRoleEntity;
    
    
    }

    @Override
    public UserRoleEntity deleteUserRoleByID(int userRoleID) throws UserRoleNotFoundException {
        UserRoleEntity userRoleEntity = this.getUserRoleByID(userRoleID);
        userRoleEntity.setIsDeleted(true);
        em.persist(userRoleEntity);
        return userRoleEntity;

    }

    public void persist(Object object) {
        em.persist(object);
    }
}
