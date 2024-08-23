/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testDefaultValueBean;

import com.dreamersoft.hms.entity.TestDefaultValueEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface TestDefaultValueManagerRemote {

public TestDefaultValueEntity getTestDefaultValueByID(int testDefaultValueID);

    
}
