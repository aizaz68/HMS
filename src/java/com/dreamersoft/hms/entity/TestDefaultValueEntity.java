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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "test_default_value")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestDefaultValueEntity.findAll", query = "SELECT t FROM TestDefaultValueEntity t"),
    @NamedQuery(name = "TestDefaultValueEntity.findByTestDefaultValueId", query = "SELECT t FROM TestDefaultValueEntity t WHERE t.testDefaultValueId = :testDefaultValueId"),
    @NamedQuery(name = "TestDefaultValueEntity.findByTestDefaultValue", query = "SELECT t FROM TestDefaultValueEntity t WHERE t.testDefaultValue = :testDefaultValue"),
    @NamedQuery(name = "TestDefaultValueEntity.findByIsMale", query = "SELECT t FROM TestDefaultValueEntity t WHERE t.isMale = :isMale"),
    @NamedQuery(name = "TestDefaultValueEntity.findByIsDeleted", query = "SELECT t FROM TestDefaultValueEntity t WHERE t.isDeleted = :isDeleted")})
public class TestDefaultValueEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "test_default_value_id")
    private Integer testDefaultValueId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "test_default_value")
    private double testDefaultValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_male")
    private short isMale;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testDefaultValueId")
    private List<TestEntity> testEntityList;

    public TestDefaultValueEntity() {
    }

    public TestDefaultValueEntity(Integer testDefaultValueId) {
        this.testDefaultValueId = testDefaultValueId;
    }

    public TestDefaultValueEntity(Integer testDefaultValueId, double testDefaultValue, short isMale, short isDeleted) {
        this.testDefaultValueId = testDefaultValueId;
        this.testDefaultValue = testDefaultValue;
        this.isMale = isMale;
        this.isDeleted = isDeleted;
    }

    public Integer getTestDefaultValueId() {
        return testDefaultValueId;
    }

    public void setTestDefaultValueId(Integer testDefaultValueId) {
        this.testDefaultValueId = testDefaultValueId;
    }

    public double getTestDefaultValue() {
        return testDefaultValue;
    }

    public void setTestDefaultValue(double testDefaultValue) {
        this.testDefaultValue = testDefaultValue;
    }

    public short getIsMale() {
        return isMale;
    }

    public void setIsMale(short isMale) {
        this.isMale = isMale;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<TestEntity> getTestEntityList() {
        return testEntityList;
    }

    public void setTestEntityList(List<TestEntity> testEntityList) {
        this.testEntityList = testEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testDefaultValueId != null ? testDefaultValueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestDefaultValueEntity)) {
            return false;
        }
        TestDefaultValueEntity other = (TestDefaultValueEntity) object;
        if ((this.testDefaultValueId == null && other.testDefaultValueId != null) || (this.testDefaultValueId != null && !this.testDefaultValueId.equals(other.testDefaultValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.TestDefaultValueEntity[ testDefaultValueId=" + testDefaultValueId + " ]";
    }
    
}
