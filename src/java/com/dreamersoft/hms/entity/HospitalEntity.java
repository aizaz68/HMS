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
@Table(name = "hospital")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HospitalEntity.findAll", query = "SELECT h FROM HospitalEntity h"),
    @NamedQuery(name = "HospitalEntity.findByHospitalId", query = "SELECT h FROM HospitalEntity h WHERE h.hospitalId = :hospitalId"),
    @NamedQuery(name = "HospitalEntity.findByHospitalName", query = "SELECT h FROM HospitalEntity h WHERE h.hospitalName = :hospitalName"),
    @NamedQuery(name = "HospitalEntity.findByHospitalAddress", query = "SELECT h FROM HospitalEntity h WHERE h.hospitalAddress = :hospitalAddress"),
    @NamedQuery(name = "HospitalEntity.findByHospitalContactNumber", query = "SELECT h FROM HospitalEntity h WHERE h.hospitalContactNumber = :hospitalContactNumber"),
    @NamedQuery(name = "HospitalEntity.findByHospitalMail", query = "SELECT h FROM HospitalEntity h WHERE h.hospitalMail = :hospitalMail"),
    @NamedQuery(name = "HospitalEntity.findByIsDeleted", query = "SELECT h FROM HospitalEntity h WHERE h.isDeleted = :isDeleted")})
public class HospitalEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hospital_id")
    private Integer hospitalId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hospital_name")
    private String hospitalName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "hospital_address")
    private String hospitalAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hospital_contact_number")
    private String hospitalContactNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hospital_mail")
    private String hospitalMail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalId")
    private List<DoctorEntity> doctorEntityList;

    public HospitalEntity() {
    }

    public HospitalEntity(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public HospitalEntity(Integer hospitalId, String hospitalName, String hospitalAddress, String hospitalContactNumber, String hospitalMail, boolean isDeleted) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.hospitalContactNumber = hospitalContactNumber;
        this.hospitalMail = hospitalMail;
        this.isDeleted = isDeleted;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalContactNumber() {
        return hospitalContactNumber;
    }

    public void setHospitalContactNumber(String hospitalContactNumber) {
        this.hospitalContactNumber = hospitalContactNumber;
    }

    public String getHospitalMail() {
        return hospitalMail;
    }

    public void setHospitalMail(String hospitalMail) {
        this.hospitalMail = hospitalMail;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<DoctorEntity> getDoctorEntityList() {
        return doctorEntityList;
    }

    public void setDoctorEntityList(List<DoctorEntity> doctorEntityList) {
        this.doctorEntityList = doctorEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hospitalId != null ? hospitalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HospitalEntity)) {
            return false;
        }
        HospitalEntity other = (HospitalEntity) object;
        if ((this.hospitalId == null && other.hospitalId != null) || (this.hospitalId != null && !this.hospitalId.equals(other.hospitalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.HospitalEntity[ hospitalId=" + hospitalId + " ]";
    }
    
}
