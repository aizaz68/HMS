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
 * @author Home
 */
@Entity
@Table(name = "appointment_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppointmentStatusEntity.findAll", query = "SELECT a FROM AppointmentStatusEntity a"),
    @NamedQuery(name = "AppointmentStatusEntity.findByAppointmentStatusId", query = "SELECT a FROM AppointmentStatusEntity a WHERE a.appointmentStatusId = :appointmentStatusId"),
    @NamedQuery(name = "AppointmentStatusEntity.findByAppointmentStatusName", query = "SELECT a FROM AppointmentStatusEntity a WHERE a.appointmentStatusName = :appointmentStatusName"),
    @NamedQuery(name = "AppointmentStatusEntity.findByIsDeleted", query = "SELECT a FROM AppointmentStatusEntity a WHERE a.isDeleted = :isDeleted")})
public class AppointmentStatusEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appointment_status_id")
    private Integer appointmentStatusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "appointment_status_name")
    private String appointmentStatusName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appointmentStatusId")
    private List<AppointmentEntity> appointmentEntityList;

    public AppointmentStatusEntity() {
    }

    public AppointmentStatusEntity(Integer appointmentStatusId) {
        this.appointmentStatusId = appointmentStatusId;
    }

    public AppointmentStatusEntity(Integer appointmentStatusId, String appointmentStatusName, boolean isDeleted) {
        this.appointmentStatusId = appointmentStatusId;
        this.appointmentStatusName = appointmentStatusName;
        this.isDeleted = isDeleted;
    }

    public Integer getAppointmentStatusId() {
        return appointmentStatusId;
    }

    public void setAppointmentStatusId(Integer appointmentStatusId) {
        this.appointmentStatusId = appointmentStatusId;
    }

    public String getAppointmentStatusName() {
        return appointmentStatusName;
    }

    public void setAppointmentStatusName(String appointmentStatusName) {
        this.appointmentStatusName = appointmentStatusName;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
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
        hash += (appointmentStatusId != null ? appointmentStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppointmentStatusEntity)) {
            return false;
        }
        AppointmentStatusEntity other = (AppointmentStatusEntity) object;
        if ((this.appointmentStatusId == null && other.appointmentStatusId != null) || (this.appointmentStatusId != null && !this.appointmentStatusId.equals(other.appointmentStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.AppointmentStatusEntity[ appointmentStatusId=" + appointmentStatusId + " ]";
    }
    
}
