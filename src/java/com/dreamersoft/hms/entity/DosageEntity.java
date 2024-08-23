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
@Table(name = "dosage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DosageEntity.findAll", query = "SELECT d FROM DosageEntity d"),
    @NamedQuery(name = "DosageEntity.findByDosageId", query = "SELECT d FROM DosageEntity d WHERE d.dosageId = :dosageId"),
    @NamedQuery(name = "DosageEntity.findByDosageDescription", query = "SELECT d FROM DosageEntity d WHERE d.dosageDescription = :dosageDescription"),
    @NamedQuery(name = "DosageEntity.findByIsDeleted", query = "SELECT d FROM DosageEntity d WHERE d.isDeleted = :isDeleted")})
public class DosageEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dosage_id")
    private Integer dosageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "dosage_description")
    private String dosageDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dosageId")
    private List<DoctorMedicineEntity> doctorMedicineEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dosageId")
    private List<AdviseEntity> adviseEntityList;

    public DosageEntity() {
    }

    public DosageEntity(Integer dosageId) {
        this.dosageId = dosageId;
    }

    public DosageEntity(Integer dosageId, String dosageDescription, short isDeleted) {
        this.dosageId = dosageId;
        this.dosageDescription = dosageDescription;
        this.isDeleted = isDeleted;
    }

    public Integer getDosageId() {
        return dosageId;
    }

    public void setDosageId(Integer dosageId) {
        this.dosageId = dosageId;
    }

    public String getDosageDescription() {
        return dosageDescription;
    }

    public void setDosageDescription(String dosageDescription) {
        this.dosageDescription = dosageDescription;
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
        hash += (dosageId != null ? dosageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DosageEntity)) {
            return false;
        }
        DosageEntity other = (DosageEntity) object;
        if ((this.dosageId == null && other.dosageId != null) || (this.dosageId != null && !this.dosageId.equals(other.dosageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.DosageEntity[ dosageId=" + dosageId + " ]";
    }
    
}
