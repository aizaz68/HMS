/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.testResultsBean;

import com.dreamersoft.hms.entity.TestResultsEntity;
import javax.ejb.Remote;

/**
 *
 * @author aizaz
 */
@Remote
public interface TestResultManagerRemote {
    
public TestResultsEntity getTestResultByID(int testResultID);

}
