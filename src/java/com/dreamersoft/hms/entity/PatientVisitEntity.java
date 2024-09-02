/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dreamersoft.hms.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Home
 */
@Entity
@Table(name = "patient_visit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientVisitEntity.findAll", query = "SELECT p FROM PatientVisitEntity p"),
    @NamedQuery(name = "PatientVisitEntity.findByPatientVisitId", query = "SELECT p FROM PatientVisitEntity p WHERE p.patientVisitId = :patientVisitId"),
    @NamedQuery(name = "PatientVisitEntity.findByPatientVisitDate", query = "SELECT p FROM PatientVisitEntity p WHERE p.patientVisitDate = :patientVisitDate"),
    @NamedQuery(name = "PatientVisitEntity.findByPatientBloodPressure", query = "SELECT p FROM PatientVisitEntity p WHERE p.patientBloodPressure = :patientBloodPressure"),
    @NamedQuery(name = "PatientVisitEntity.findByPatientPulse", query = "SELECT p FROM PatientVisitEntity p WHERE p.patientPulse = :patientPulse"),
    @NamedQuery(name = "PatientVisitEntity.findByPatientTemperature", query = "SELECT p FROM PatientVisitEntity p WHERE p.patientTemperature = :patientTemperature"),
    @NamedQuery(name = "PatientVisitEntity.findByPatientWeight", query = "SELECT p FROM PatientVisitEntity p WHERE p.patientWeight = :patientWeight"),
    @NamedQuery(name = "PatientVisitEntity.findByPatientAge", query = "SELECT p FROM PatientVisitEntity p WHERE p.patientAge = :patientAge"),
    @NamedQuery(name = "PatientVisitEntity.findByDiagnosis1", query = "SELECT p FROM PatientVisitEntity p WHERE p.diagnosis1 = :diagnosis1"),
    @NamedQuery(name = "PatientVisitEntity.findByDiagnosis2", query = "SELECT p FROM PatientVisitEntity p WHERE p.diagnosis2 = :diagnosis2"),
    @NamedQuery(name = "PatientVisitEntity.findByDiagnosis3", query = "SELECT p FROM PatientVisitEntity p WHERE p.diagnosis3 = :diagnosis3"),
    @NamedQuery(name = "PatientVisitEntity.findByPatientVisitInstructions", query = "SELECT p FROM PatientVisitEntity p WHERE p.patientVisitInstructions = :patientVisitInstructions"),
    @NamedQuery(name = "PatientVisitEntity.findByIsDeleted", query = "SELECT p FROM PatientVisitEntity p WHERE p.isDeleted = :isDeleted")})
public class PatientVisitEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "patient_visit_id")
    private Integer patientVisitId;
    @Column(name = "patient_visit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date patientVisitDate;
    @Size(max = 45)
    @Column(name = "patient_blood_pressure")
    private String patientBloodPressure;
    @Column(name = "patient_pulse")
    private Integer patientPulse;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "patient_temperature")
    private Float patientTemperature;
    @Column(name = "patient_weight")
    private Float patientWeight;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "patient_age")
    private int patientAge;
    @Size(max = 45)
    @Column(name = "diagnosis1")
    private String diagnosis1;
    @Size(max = 45)
    @Column(name = "diagnosis2")
    private String diagnosis2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "diagnosis3")
    private String diagnosis3;
    @Size(max = 150)
    @Column(name = "patient_visit_instructions")
    private String patientVisitInstructions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    @ManyToOne(optional = false)
    private DoctorEntity doctorId;
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    @ManyToOne(optional = false)
    private PatientEntity patientId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientVisitId")
    private List<AdviseEntity> adviseEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientVisitId")
    private List<PatientVisitTestEntity> patientVisitTestEntityList;

    public PatientVisitEntity() {
    }

    public PatientVisitEntity(Integer patientVisitId) {
        this.patientVisitId = patientVisitId;
    }

    public PatientVisitEntity(Integer patientVisitId, int patientAge, String diagnosis3, boolean isDeleted) {
        this.patientVisitId = patientVisitId;
        this.patientAge = patientAge;
        this.diagnosis3 = diagnosis3;
        this.isDeleted = isDeleted;
    }

    public Integer getPatientVisitId() {
        return patientVisitId;
    }

    public void setPatientVisitId(Integer patientVisitId) {
        this.patientVisitId = patientVisitId;
    }

    public Date getPatientVisitDate() {
        return patientVisitDate;
    }

    public void setPatientVisitDate(Date patientVisitDate) {
        this.patientVisitDate = patientVisitDate;
    }

    public String getPatientBloodPressure() {
        return patientBloodPressure;
    }

    public void setPatientBloodPressure(String patientBloodPressure) {
        this.patientBloodPressure = patientBloodPressure;
    }

    public Integer getPatientPulse() {
        return patientPulse;
    }

    public void setPatientPulse(Integer patientPulse) {
        this.patientPulse = patientPulse;
    }

    public Float getPatientTemperature() {
        return patientTemperature;
    }

    public void setPatientTemperature(Float patientTemperature) {
        this.patientTemperature = patientTemperature;
    }

    public Float getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(Float patientWeight) {
        this.patientWeight = patientWeight;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getDiagnosis1() {
        return diagnosis1;
    }

    public void setDiagnosis1(String diagnosis1) {
        this.diagnosis1 = diagnosis1;
    }

    public String getDiagnosis2() {
        return diagnosis2;
    }

    public void setDiagnosis2(String diagnosis2) {
        this.diagnosis2 = diagnosis2;
    }

    public String getDiagnosis3() {
        return diagnosis3;
    }

    public void setDiagnosis3(String diagnosis3) {
        this.diagnosis3 = diagnosis3;
    }

    public String getPatientVisitInstructions() {
        return patientVisitInstructions;
    }

    public void setPatientVisitInstructions(String patientVisitInstructions) {
        this.patientVisitInstructions = patientVisitInstructions;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    @XmlTransient
    public List<AdviseEntity> getAdviseEntityList() {
        return adviseEntityList;
    }

    public void setAdviseEntityList(List<AdviseEntity> adviseEntityList) {
        this.adviseEntityList = adviseEntityList;
    }

    @XmlTransient
    public List<PatientVisitTestEntity> getPatientVisitTestEntityList() {
        return patientVisitTestEntityList;
    }

    public void setPatientVisitTestEntityList(List<PatientVisitTestEntity> patientVisitTestEntityList) {
        this.patientVisitTestEntityList = patientVisitTestEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientVisitId != null ? patientVisitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientVisitEntity)) {
            return false;
        }
        PatientVisitEntity other = (PatientVisitEntity) object;
        if ((this.patientVisitId == null && other.patientVisitId != null) || (this.patientVisitId != null && !this.patientVisitId.equals(other.patientVisitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.PatientVisitEntity[ patientVisitId=" + patientVisitId + " ]";
    }
    
}
