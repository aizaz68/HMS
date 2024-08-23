/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dreamersoft.hms.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "test_results")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestResultsEntity.findAll", query = "SELECT t FROM TestResultsEntity t"),
    @NamedQuery(name = "TestResultsEntity.findByTestResultId", query = "SELECT t FROM TestResultsEntity t WHERE t.testResultId = :testResultId"),
    @NamedQuery(name = "TestResultsEntity.findByTestResultValue", query = "SELECT t FROM TestResultsEntity t WHERE t.testResultValue = :testResultValue"),
    @NamedQuery(name = "TestResultsEntity.findByTestResultDate", query = "SELECT t FROM TestResultsEntity t WHERE t.testResultDate = :testResultDate"),
    @NamedQuery(name = "TestResultsEntity.findByIsDeleted", query = "SELECT t FROM TestResultsEntity t WHERE t.isDeleted = :isDeleted")})
public class TestResultsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "test_result_id")
    private Integer testResultId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "test_result_value")
    private double testResultValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "test_result_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testResultDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @JoinColumn(name = "patient_visit_test_id", referencedColumnName = "patient_visit_test_id")
    @ManyToOne(optional = false)
    private PatientVisitTestEntity patientVisitTestId;

    public TestResultsEntity() {
    }

    public TestResultsEntity(Integer testResultId) {
        this.testResultId = testResultId;
    }

    public TestResultsEntity(Integer testResultId, double testResultValue, Date testResultDate, short isDeleted) {
        this.testResultId = testResultId;
        this.testResultValue = testResultValue;
        this.testResultDate = testResultDate;
        this.isDeleted = isDeleted;
    }

    public Integer getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Integer testResultId) {
        this.testResultId = testResultId;
    }

    public double getTestResultValue() {
        return testResultValue;
    }

    public void setTestResultValue(double testResultValue) {
        this.testResultValue = testResultValue;
    }

    public Date getTestResultDate() {
        return testResultDate;
    }

    public void setTestResultDate(Date testResultDate) {
        this.testResultDate = testResultDate;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public PatientVisitTestEntity getPatientVisitTestId() {
        return patientVisitTestId;
    }

    public void setPatientVisitTestId(PatientVisitTestEntity patientVisitTestId) {
        this.patientVisitTestId = patientVisitTestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testResultId != null ? testResultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestResultsEntity)) {
            return false;
        }
        TestResultsEntity other = (TestResultsEntity) object;
        if ((this.testResultId == null && other.testResultId != null) || (this.testResultId != null && !this.testResultId.equals(other.testResultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.TestResultsEntity[ testResultId=" + testResultId + " ]";
    }
    
}
