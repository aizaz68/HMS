/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.roleBean;

import com.dreamersoft.hms.entity.RoleEntity;
import javax.ejb.Stateless;

/**
 *
 * @author aizaz
 */
@Stateless
public class RoleManger implements RoleMangerRemote {

    @Override
    public RoleEntity getRoleByID(int roleID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
