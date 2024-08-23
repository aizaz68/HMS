/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dreamersoft.hms.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "appointment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppointmentEntity.findAll", query = "SELECT a FROM AppointmentEntity a"),
    @NamedQuery(name = "AppointmentEntity.findByAppointmentId", query = "SELECT a FROM AppointmentEntity a WHERE a.appointmentId = :appointmentId"),
    @NamedQuery(name = "AppointmentEntity.findByAppointmentTime", query = "SELECT a FROM AppointmentEntity a WHERE a.appointmentTime = :appointmentTime"),
    @NamedQuery(name = "AppointmentEntity.findByTokenNumber", query = "SELECT a FROM AppointmentEntity a WHERE a.tokenNumber = :tokenNumber"),
    @NamedQuery(name = "AppointmentEntity.findByIsDeleted", query = "SELECT a FROM AppointmentEntity a WHERE a.isDeleted = :isDeleted")})
public class AppointmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appointment_id")
    private Integer appointmentId;
    @Column(name = "appointment_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentTime;
    @Column(name = "token_number")
    private Integer tokenNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @JoinColumn(name = "appointment_status_id", referencedColumnName = "appointment_status_id")
    @ManyToOne(optional = false)
    private AppointmentStatusEntity appointmentStatusId;
    @JoinColumn(name = "appointment_type_id", referencedColumnName = "appointment_type_id")
    @ManyToOne(optional = false)
    private AppointmentTypeEntity appointmentTypeId;
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    @ManyToOne(optional = false)
    private DoctorEntity doctorId;
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    @ManyToOne(optional = false)
    private PatientEntity patientId;

    public AppointmentEntity() {
    }

    public AppointmentEntity(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public AppointmentEntity(Integer appointmentId, short isDeleted) {
        this.appointmentId = appointmentId;
        this.isDeleted = isDeleted;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(Integer tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public AppointmentStatusEntity getAppointmentStatusId() {
        return appointmentStatusId;
    }

    public void setAppointmentStatusId(AppointmentStatusEntity appointmentStatusId) {
        this.appointmentStatusId = appointmentStatusId;
    }

    public AppointmentTypeEntity getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(AppointmentTypeEntity appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public DoctorEntity getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(DoctorEntity doctorId) {
        this.doctorId = doctorId;
    }

    public PatientEntity getPatientId() {
        return patientId;
    }

    public void setPatientId(PatientEntity patientId) {
        this.patientId = patientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentId != null ? appointmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppointmentEntity)) {
            return false;
        }
        AppointmentEntity other = (AppointmentEntity) object;
        if ((this.appointmentId == null && other.appointmentId != null) || (this.appointmentId != null && !this.appointmentId.equals(other.appointmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.AppointmentEntity[ appointmentId=" + appointmentId + " ]";
    }
    
}
