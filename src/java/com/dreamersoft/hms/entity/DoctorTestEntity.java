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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "doctor_test")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DoctorTestEntity.findAll", query = "SELECT d FROM DoctorTestEntity d"),
    @NamedQuery(name = "DoctorTestEntity.findByDoctorTestId", query = "SELECT d FROM DoctorTestEntity d WHERE d.doctorTestId = :doctorTestId"),
    @NamedQuery(name = "DoctorTestEntity.findByIsDeleted", query = "SELECT d FROM DoctorTestEntity d WHERE d.isDeleted = :isDeleted")})
public class DoctorTestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "doctor_test_id")
    private Integer doctorTestId;
    @Column(name = "is_deleted")
    private Short isDeleted;
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    @ManyToOne(optional = false)
    private DoctorEntity doctorId;
    @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    @ManyToOne(optional = false)
    private TestEntity testId;

    public DoctorTestEntity() {
    }

    public DoctorTestEntity(Integer doctorTestId) {
        this.doctorTestId = doctorTestId;
    }

    public Integer getDoctorTestId() {
        return doctorTestId;
    }

    public void setDoctorTestId(Integer doctorTestId) {
        this.doctorTestId = doctorTestId;
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

    public TestEntity getTestId() {
        return testId;
    }

    public void setTestId(TestEntity testId) {
        this.testId = testId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (doctorTestId != null ? doctorTestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DoctorTestEntity)) {
            return false;
        }
        DoctorTestEntity other = (DoctorTestEntity) object;
        if ((this.doctorTestId == null && other.doctorTestId != null) || (this.doctorTestId != null && !this.doctorTestId.equals(other.doctorTestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.DoctorTestEntity[ doctorTestId=" + doctorTestId + " ]";
    }
    
}
