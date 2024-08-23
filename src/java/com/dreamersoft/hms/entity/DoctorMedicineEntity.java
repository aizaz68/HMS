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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "doctor_medicine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DoctorMedicineEntity.findAll", query = "SELECT d FROM DoctorMedicineEntity d"),
    @NamedQuery(name = "DoctorMedicineEntity.findByDoctorMedicineId", query = "SELECT d FROM DoctorMedicineEntity d WHERE d.doctorMedicineId = :doctorMedicineId"),
    @NamedQuery(name = "DoctorMedicineEntity.findByDefaultDays", query = "SELECT d FROM DoctorMedicineEntity d WHERE d.defaultDays = :defaultDays"),
    @NamedQuery(name = "DoctorMedicineEntity.findByIsDeleted", query = "SELECT d FROM DoctorMedicineEntity d WHERE d.isDeleted = :isDeleted")})
public class DoctorMedicineEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "doctor_medicine_id")
    private Integer doctorMedicineId;
    @Column(name = "default_days")
    private Integer defaultDays;
    @Column(name = "is_deleted")
    private Short isDeleted;
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    @ManyToOne(optional = false)
    private DoctorEntity doctorId;
    @JoinColumn(name = "dosage_id", referencedColumnName = "dosage_id")
    @ManyToOne(optional = false)
    private DosageEntity dosageId;
    @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id")
    @ManyToOne(optional = false)
    private MedicineEntity medicineId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorMedicineId")
    private List<AdviseEntity> adviseEntityList;

    public DoctorMedicineEntity() {
    }

    public DoctorMedicineEntity(Integer doctorMedicineId) {
        this.doctorMedicineId = doctorMedicineId;
    }

    public Integer getDoctorMedicineId() {
        return doctorMedicineId;
    }

    public void setDoctorMedicineId(Integer doctorMedicineId) {
        this.doctorMedicineId = doctorMedicineId;
    }

    public Integer getDefaultDays() {
        return defaultDays;
    }

    public void setDefaultDays(Integer defaultDays) {
        this.defaultDays = defaultDays;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public DoctorEntity getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(DoctorEntity doctorId) {
        this.doctorId = doctorId;
    }

    public DosageEntity getDosageId() {
        return dosageId;
    }

    public void setDosageId(DosageEntity dosageId) {
        this.dosageId = dosageId;
    }

    public MedicineEntity getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(MedicineEntity medicineId) {
        this.medicineId = medicineId;
    }

    @XmlTransient
    public List<AdviseEntity> getAdviseEntityList() {
        return adviseEntityList;
    }

    public void setAdviseEntityList(List<AdviseEntity> adviseEntityList) {
        this.adviseEntityList = adviseEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (doctorMedicineId != null ? doctorMedicineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DoctorMedicineEntity)) {
            return false;
        }
        DoctorMedicineEntity other = (DoctorMedicineEntity) object;
        if ((this.doctorMedicineId == null && other.doctorMedicineId != null) || (this.doctorMedicineId != null && !this.doctorMedicineId.equals(other.doctorMedicineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.DoctorMedicineEntity[ doctorMedicineId=" + doctorMedicineId + " ]";
    }
    
}
