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
@Table(name = "test")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestEntity.findAll", query = "SELECT t FROM TestEntity t"),
    @NamedQuery(name = "TestEntity.findByTestId", query = "SELECT t FROM TestEntity t WHERE t.testId = :testId"),
    @NamedQuery(name = "TestEntity.findByTestName", query = "SELECT t FROM TestEntity t WHERE t.testName = :testName"),
    @NamedQuery(name = "TestEntity.findByIsDeleted", query = "SELECT t FROM TestEntity t WHERE t.isDeleted = :isDeleted")})
public class TestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "test_id")
    private Integer testId;
    @Size(max = 45)
    @Column(name = "test_name")
    private String testName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @JoinColumn(name = "test_default_value_id", referencedColumnName = "test_default_value_id")
    @ManyToOne(optional = false)
    private TestDefaultValueEntity testDefaultValueId;
    @JoinColumn(name = "test_unit_id", referencedColumnName = "test_unit_id")
    @ManyToOne(optional = false)
    private TestUnitEntity testUnitId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testId")
    private List<DoctorTestEntity> doctorTestEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testId")
    private List<PatientVisitTestEntity> patientVisitTestEntityList;

    public TestEntity() {
    }

    public TestEntity(Integer testId) {
        this.testId = testId;
    }

    public TestEntity(Integer testId, short isDeleted) {
        this.testId = testId;
        this.isDeleted = isDeleted;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public TestDefaultValueEntity getTestDefaultValueId() {
        return testDefaultValueId;
    }

    public void setTestDefaultValueId(TestDefaultValueEntity testDefaultValueId) {
        this.testDefaultValueId = testDefaultValueId;
    }

    public TestUnitEntity getTestUnitId() {
        return testUnitId;
    }

    public void setTestUnitId(TestUnitEntity testUnitId) {
        this.testUnitId = testUnitId;
    }

    @XmlTransient
    public List<DoctorTestEntity> getDoctorTestEntityList() {
        return doctorTestEntityList;
    }

    public void setDoctorTestEntityList(List<DoctorTestEntity> doctorTestEntityList) {
        this.doctorTestEntityList = doctorTestEntityList;
    }

    @XmlTransient
    public List<PatientVisitTestEntity> getPatientVisitTestEntityList() {
        return patientVisitTestEntityList;
    }

    public void setPatientVisitTestEntityList(List<PatientVisitTestEntity> patientVisitTestEntityList) {
        this.patientVisitTestEntityList = patientVisitTestEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testId != null ? testId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestEntity)) {
            return false;
        }
        TestEntity other = (TestEntity) object;
        if ((this.testId == null && other.testId != null) || (this.testId != null && !this.testId.equals(other.testId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.TestEntity[ testId=" + testId + " ]";
    }
    
}
