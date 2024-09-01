/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dreamersoft.hms.sessionbeans.appointmentBean;

/**
 *
 * @author aizaz
 */
public class AppointmentAlreadyExistsException extends Exception {
    public AppointmentAlreadyExistsException(String msg){
    super(msg);
    }
}
