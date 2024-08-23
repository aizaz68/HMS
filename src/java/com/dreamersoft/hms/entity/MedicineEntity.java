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
@Table(name = "medicine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicineEntity.findAll", query = "SELECT m FROM MedicineEntity m"),
    @NamedQuery(name = "MedicineEntity.findByMedicineId", query = "SELECT m FROM MedicineEntity m WHERE m.medicineId = :medicineId"),
    @NamedQuery(name = "MedicineEntity.findByMedicineName", query = "SELECT m FROM MedicineEntity m WHERE m.medicineName = :medicineName"),
    @NamedQuery(name = "MedicineEntity.findByIsDeleted", query = "SELECT m FROM MedicineEntity m WHERE m.isDeleted = :isDeleted")})
public class MedicineEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "medicine_id")
    private Integer medicineId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "medicine_name")
    private String medicineName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicineId")
    private List<DoctorMedicineEntity> doctorMedicineEntityList;

    public MedicineEntity() {
    }

    public MedicineEntity(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public MedicineEntity(Integer medicineId, String medicineName, short isDeleted) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.isDeleted = isDeleted;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<DoctorMedicineEntity> getDoctorMedicineEntityList() {
        return doctorMedicineEntityList;
    }

    public void setDoctorMedicineEntityList(List<DoctorMedicineEntity> doctorMedicineEntityList) {
        this.doctorMedicineEntityList = doctorMedicineEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicineId != null ? medicineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicineEntity)) {
            return false;
        }
        MedicineEntity other = (MedicineEntity) object;
        if ((this.medicineId == null && other.medicineId != null) || (this.medicineId != null && !this.medicineId.equals(other.medicineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.MedicineEntity[ medicineId=" + medicineId + " ]";
    }
    
}
