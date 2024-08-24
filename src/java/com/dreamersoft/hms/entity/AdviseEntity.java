/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dreamersoft.hms.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "advise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdviseEntity.findAll", query = "SELECT a FROM AdviseEntity a"),
    @NamedQuery(name = "AdviseEntity.findByAdviseId", query = "SELECT a FROM AdviseEntity a WHERE a.adviseId = :adviseId"),
    @NamedQuery(name = "AdviseEntity.findByMedicineDays", query = "SELECT a FROM AdviseEntity a WHERE a.medicineDays = :medicineDays"),
    @NamedQuery(name = "AdviseEntity.findByIsDeleted", query = "SELECT a FROM AdviseEntity a WHERE a.isDeleted = :isDeleted")})
public class AdviseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "advise_id")
    private Integer adviseId;
    @Column(name = "medicine_days")
    private Integer medicineDays;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @JoinColumn(name = "doctor_medicine_id", referencedColumnName = "doctor_medicine_id")
    @ManyToOne(optional = false)
    private DoctorMedicineEntity doctorMedicineId;
    @JoinColumn(name = "dosage_id", referencedColumnName = "dosage_id")
    @ManyToOne(optional = false)
    private DosageEntity dosageId;
    @JoinColumn(name = "patient_visit_id", referencedColumnName = "patient_visit_id")
    @ManyToOne(optional = false)
    private PatientVisitEntity patientVisitId;

    public AdviseEntity() {
    }

    public AdviseEntity(Integer adviseId) {
        this.adviseId = adviseId;
    }

    public AdviseEntity(Integer adviseId, boolean isDeleted) {
        this.adviseId = adviseId;
        this.isDeleted = isDeleted;
    }

    public Integer getAdviseId() {
        return adviseId;
    }

    public void setAdviseId(Integer adviseId) {
        this.adviseId = adviseId;
    }

    public Integer getMedicineDays() {
        return medicineDays;
    }

    public void setMedicineDays(Integer medicineDays) {
        this.medicineDays = medicineDays;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public DoctorMedicineEntity getDoctorMedicineId() {
        return doctorMedicineId;
    }

    public void setDoctorMedicineId(DoctorMedicineEntity doctorMedicineId) {
        this.doctorMedicineId = doctorMedicineId;
    }

    public DosageEntity getDosageId() {
        return dosageId;
    }

    public void setDosageId(DosageEntity dosageId) {
        this.dosageId = dosageId;
    }

    public PatientVisitEntity getPatientVisitId() {
        return patientVisitId;
    }

    public void setPatientVisitId(PatientVisitEntity patientVisitId) {
        this.patientVisitId = patientVisitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adviseId != null ? adviseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdviseEntity)) {
            return false;
        }
        AdviseEntity other = (AdviseEntity) object;
        if ((this.adviseId == null && other.adviseId != null) || (this.adviseId != null && !this.adviseId.equals(other.adviseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.AdviseEntity[ adviseId=" + adviseId + " ]";
    }
    
}
