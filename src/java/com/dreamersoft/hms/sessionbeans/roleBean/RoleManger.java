/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.roleBean;

import com.dreamersoft.hms.entity.RoleEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aizaz
 */
@Stateless
public class RoleManger implements RoleMangerRemote {

    @PersistenceContext(unitName = "HospitalManagementSystemPU")
    private EntityManager em;

    
    //get role by id
    @Override
    public RoleEntity getRoleByID(int roleID) throws RoleNotFoundException{
        RoleEntity role = em.find(RoleEntity.class,roleID);
        if(role == null || role.getIsDeleted()){
            throw new RoleNotFoundException("Role not found");
        }
        return role; 
    }
    
    //add role
    @Override
    public RoleEntity addRole(String roleName){
        RoleEntity role = new RoleEntity();
        role.setRoleName(roleName);
        role.setIsDeleted(false);
        em.persist(role);
        return role;
    }
    
    //update role by id
    @Override
    public RoleEntity updateRoleById(int roleId,String newRoleName) throws RoleNotFoundException{
        RoleEntity role = getRoleByID(roleId);
        role.setRoleName(newRoleName);
        role.setIsDeleted(false);
        em.merge(role);
        return role;
    }

    //get role list
    @Override
    public List<RoleEntity> getRoleListByName(String roleName) throws RoleListNotfoundException {
        Query qry = em.createQuery("select a from RoleEntity a where a.roleName = :roleName and a.isDeleted = false", RoleEntity.class);
        qry.setParameter("roleName", roleName);
        List<RoleEntity> roleList = qry.getResultList();
        if(roleList == null || roleList.isEmpty()){
            throw new RoleListNotfoundException("Role list not found");
        }
        return roleList;
    }
    
    //delet role
    @Override
    public void deleteRole(int roleId) throws RoleNotFoundException{
        RoleEntity role = getRoleByID(roleId);
        role.setIsDeleted(true);
    }
    
    public List<RoleEntity> getAllRoles() throws RoleListNotfoundException{
        Query qry = em.createQuery("SELECT r FROM RoleEntity r WHERE r.isDeleted = false");
        List<RoleEntity> roleList = qry.getResultList();
        if(roleList.isEmpty()){
            throw new RoleListNotfoundException("No record found in Role list");
        }
        return roleList;
    }


    public void persist(Object object) {
        em.persist(object);
    }
}
