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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "patient_visit_test")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientVisitTestEntity.findAll", query = "SELECT p FROM PatientVisitTestEntity p"),
    @NamedQuery(name = "PatientVisitTestEntity.findByPatientVisitTestId", query = "SELECT p FROM PatientVisitTestEntity p WHERE p.patientVisitTestId = :patientVisitTestId"),
    @NamedQuery(name = "PatientVisitTestEntity.findByIsDeleted", query = "SELECT p FROM PatientVisitTestEntity p WHERE p.isDeleted = :isDeleted")})
public class PatientVisitTestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "patient_visit_test_id")
    private Integer patientVisitTestId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @JoinColumn(name = "patient_visit_id", referencedColumnName = "patient_visit_id")
    @ManyToOne(optional = false)
    private PatientVisitEntity patientVisitId;
    @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    @ManyToOne(optional = false)
    private TestEntity testId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientVisitTestId")
    private List<TestResultsEntity> testResultsEntityList;

    public PatientVisitTestEntity() {
    }

    public PatientVisitTestEntity(Integer patientVisitTestId) {
        this.patientVisitTestId = patientVisitTestId;
    }

    public PatientVisitTestEntity(Integer patientVisitTestId, short isDeleted) {
        this.patientVisitTestId = patientVisitTestId;
        this.isDeleted = isDeleted;
    }

    public Integer getPatientVisitTestId() {
        return patientVisitTestId;
    }

    public void setPatientVisitTestId(Integer patientVisitTestId) {
        this.patientVisitTestId = patientVisitTestId;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public PatientVisitEntity getPatientVisitId() {
        return patientVisitId;
    }

    public void setPatientVisitId(PatientVisitEntity patientVisitId) {
        this.patientVisitId = patientVisitId;
    }

    public TestEntity getTestId() {
        return testId;
    }

    public void setTestId(TestEntity testId) {
        this.testId = testId;
    }

    @XmlTransient
    public List<TestResultsEntity> getTestResultsEntityList() {
        return testResultsEntityList;
    }

    public void setTestResultsEntityList(List<TestResultsEntity> testResultsEntityList) {
        this.testResultsEntityList = testResultsEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientVisitTestId != null ? patientVisitTestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientVisitTestEntity)) {
            return false;
        }
        PatientVisitTestEntity other = (PatientVisitTestEntity) object;
        if ((this.patientVisitTestId == null && other.patientVisitTestId != null) || (this.patientVisitTestId != null && !this.patientVisitTestId.equals(other.patientVisitTestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.PatientVisitTestEntity[ patientVisitTestId=" + patientVisitTestId + " ]";
    }
    
}
