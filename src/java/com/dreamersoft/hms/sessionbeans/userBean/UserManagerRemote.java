/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.userBean;

import com.dreamersoft.hms.entity.UsersEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface UserManagerRemote {
    
    
    public UsersEntity getUserByID(int userID) throws UserNotFoundException ;
    
}
