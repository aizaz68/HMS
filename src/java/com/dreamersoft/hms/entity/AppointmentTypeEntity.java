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
@Table(name = "appointment_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppointmentTypeEntity.findAll", query = "SELECT a FROM AppointmentTypeEntity a"),
    @NamedQuery(name = "AppointmentTypeEntity.findByAppointmentTypeId", query = "SELECT a FROM AppointmentTypeEntity a WHERE a.appointmentTypeId = :appointmentTypeId"),
    @NamedQuery(name = "AppointmentTypeEntity.findByAppointmentTypeDescription", query = "SELECT a FROM AppointmentTypeEntity a WHERE a.appointmentTypeDescription = :appointmentTypeDescription"),
    @NamedQuery(name = "AppointmentTypeEntity.findByIsDeleted", query = "SELECT a FROM AppointmentTypeEntity a WHERE a.isDeleted = :isDeleted")})
public class AppointmentTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appointment_type_id")
    private Integer appointmentTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "appointment_type_description")
    private String appointmentTypeDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appointmentTypeId")
    private List<AppointmentEntity> appointmentEntityList;

    public AppointmentTypeEntity() {
    }

    public AppointmentTypeEntity(Integer appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public AppointmentTypeEntity(Integer appointmentTypeId, String appointmentTypeDescription, short isDeleted) {
        this.appointmentTypeId = appointmentTypeId;
        this.appointmentTypeDescription = appointmentTypeDescription;
        this.isDeleted = isDeleted;
    }

    public Integer getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(Integer appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public String getAppointmentTypeDescription() {
        return appointmentTypeDescription;
    }

    public void setAppointmentTypeDescription(String appointmentTypeDescription) {
        this.appointmentTypeDescription = appointmentTypeDescription;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentTypeId != null ? appointmentTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppointmentTypeEntity)) {
            return false;
        }
        AppointmentTypeEntity other = (AppointmentTypeEntity) object;
        if ((this.appointmentTypeId == null && other.appointmentTypeId != null) || (this.appointmentTypeId != null && !this.appointmentTypeId.equals(other.appointmentTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.AppointmentTypeEntity[ appointmentTypeId=" + appointmentTypeId + " ]";
    }
    
}
