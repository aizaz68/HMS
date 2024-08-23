/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dreamersoft.hms.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "patient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientEntity.findAll", query = "SELECT p FROM PatientEntity p"),
    @NamedQuery(name = "PatientEntity.findByPatientId", query = "SELECT p FROM PatientEntity p WHERE p.patientId = :patientId"),
    @NamedQuery(name = "PatientEntity.findByPatientName", query = "SELECT p FROM PatientEntity p WHERE p.patientName = :patientName"),
    @NamedQuery(name = "PatientEntity.findByPatientGender", query = "SELECT p FROM PatientEntity p WHERE p.patientGender = :patientGender"),
    @NamedQuery(name = "PatientEntity.findByPatientAddress", query = "SELECT p FROM PatientEntity p WHERE p.patientAddress = :patientAddress"),
    @NamedQuery(name = "PatientEntity.findByPatientPhoneNumber", query = "SELECT p FROM PatientEntity p WHERE p.patientPhoneNumber = :patientPhoneNumber"),
    @NamedQuery(name = "PatientEntity.findByPatientNumber", query = "SELECT p FROM PatientEntity p WHERE p.patientNumber = :patientNumber"),
    @NamedQuery(name = "PatientEntity.findByIsDeleted", query = "SELECT p FROM PatientEntity p WHERE p.isDeleted = :isDeleted")})
public class PatientEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "patient_id")
    private Integer patientId;
    @Size(max = 45)
    @Column(name = "patient_name")
    private String patientName;
    @Column(name = "patient_gender")
    private Short patientGender;
    @Size(max = 45)
    @Column(name = "patient_address")
    private String patientAddress;
    @Size(max = 45)
    @Column(name = "patient_phone_number")
    private String patientPhoneNumber;
    @Column(name = "patient_number")
    private Integer patientNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private List<AppointmentEntity> appointmentEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private List<PatientVisitEntity> patientVisitEntityList;
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    @ManyToOne(optional = false)
    private DoctorEntity doctorId;

    public PatientEntity() {
    }

    public PatientEntity(Integer patientId) {
        this.patientId = patientId;
    }

    public PatientEntity(Integer patientId, short isDeleted) {
        this.patientId = patientId;
        this.isDeleted = isDeleted;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Short getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(Short patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    public Integer getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(Integer patientNumber) {
        this.patientNumber = patientNumber;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<AppointmentEntity> getAppointmentEntityList() {
        return appointmentEntityList;
    }

    public void setAppointmentEntityList(List<AppointmentEntity> appointmentEntityList) {
        this.appointmentEntityList = appointmentEntityList;
    }

    @XmlTransient
    public List<PatientVisitEntity> getPatientVisitEntityList() {
        return patientVisitEntityList;
    }

    public void setPatientVisitEntityList(List<PatientVisitEntity> patientVisitEntityList) {
        this.patientVisitEntityList = patientVisitEntityList;
    }

    public DoctorEntity getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(DoctorEntity doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientId != null ? patientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientEntity)) {
            return false;
        }
        PatientEntity other = (PatientEntity) object;
        if ((this.patientId == null && other.patientId != null) || (this.patientId != null && !this.patientId.equals(other.patientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.PatientEntity[ patientId=" + patientId + " ]";
    }
    
}
