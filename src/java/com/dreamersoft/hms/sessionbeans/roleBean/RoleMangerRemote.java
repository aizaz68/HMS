/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.roleBean;

import com.dreamersoft.hms.entity.RoleEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface RoleMangerRemote {


public RoleEntity getRoleByID(int roleID);



    
}
