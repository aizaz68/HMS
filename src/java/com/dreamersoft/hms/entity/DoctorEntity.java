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
@Table(name = "doctor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DoctorEntity.findAll", query = "SELECT d FROM DoctorEntity d"),
    @NamedQuery(name = "DoctorEntity.findByDoctorId", query = "SELECT d FROM DoctorEntity d WHERE d.doctorId = :doctorId"),
    @NamedQuery(name = "DoctorEntity.findByDoctorCnic", query = "SELECT d FROM DoctorEntity d WHERE d.doctorCnic = :doctorCnic"),
    @NamedQuery(name = "DoctorEntity.findByIsDeleted", query = "SELECT d FROM DoctorEntity d WHERE d.isDeleted = :isDeleted")})
public class DoctorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "doctor_id")
    private Integer doctorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "doctor_cnic")
    private String doctorCnic;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorId")
    private List<DoctorMedicineEntity> doctorMedicineEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorId")
    private List<DoctorTestEntity> doctorTestEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorId")
    private List<AppointmentEntity> appointmentEntityList;
    @JoinColumn(name = "hospital_id", referencedColumnName = "hospital_id")
    @ManyToOne(optional = false)
    private HospitalEntity hospitalId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UsersEntity userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorId")
    private List<PatientVisitEntity> patientVisitEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorId")
    private List<PatientEntity> patientEntityList;

    public DoctorEntity() {
    }

    public DoctorEntity(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public DoctorEntity(Integer doctorId, String doctorCnic, short isDeleted) {
        this.doctorId = doctorId;
        this.doctorCnic = doctorCnic;
        this.isDeleted = isDeleted;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorCnic() {
        return doctorCnic;
    }

    public void setDoctorCnic(String doctorCnic) {
        this.doctorCnic = doctorCnic;
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
    public List<DoctorTestEntity> getDoctorTestEntityList() {
        return doctorTestEntityList;
    }

    public void setDoctorTestEntityList(List<DoctorTestEntity> doctorTestEntityList) {
        this.doctorTestEntityList = doctorTestEntityList;
    }

    @XmlTransient
    public List<AppointmentEntity> getAppointmentEntityList() {
        return appointmentEntityList;
    }

    public void setAppointmentEntityList(List<AppointmentEntity> appointmentEntityList) {
        this.appointmentEntityList = appointmentEntityList;
    }

    public HospitalEntity getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(HospitalEntity hospitalId) {
        this.hospitalId = hospitalId;
    }

    public UsersEntity getUserId() {
        return userId;
    }

    public void setUserId(UsersEntity userId) {
        this.userId = userId;
    }

    @XmlTransient
    public List<PatientVisitEntity> getPatientVisitEntityList() {
        return patientVisitEntityList;
    }

    public void setPatientVisitEntityList(List<PatientVisitEntity> patientVisitEntityList) {
        this.patientVisitEntityList = patientVisitEntityList;
    }

    @XmlTransient
    public List<PatientEntity> getPatientEntityList() {
        return patientEntityList;
    }

    public void setPatientEntityList(List<PatientEntity> patientEntityList) {
        this.patientEntityList = patientEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (doctorId != null ? doctorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DoctorEntity)) {
            return false;
        }
        DoctorEntity other = (DoctorEntity) object;
        if ((this.doctorId == null && other.doctorId != null) || (this.doctorId != null && !this.doctorId.equals(other.doctorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.DoctorEntity[ doctorId=" + doctorId + " ]";
    }
    
}
