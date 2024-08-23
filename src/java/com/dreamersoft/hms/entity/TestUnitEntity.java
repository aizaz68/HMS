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
@Table(name = "test_unit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestUnitEntity.findAll", query = "SELECT t FROM TestUnitEntity t"),
    @NamedQuery(name = "TestUnitEntity.findByTestUnitId", query = "SELECT t FROM TestUnitEntity t WHERE t.testUnitId = :testUnitId"),
    @NamedQuery(name = "TestUnitEntity.findByTestUnit", query = "SELECT t FROM TestUnitEntity t WHERE t.testUnit = :testUnit"),
    @NamedQuery(name = "TestUnitEntity.findByIsDeleted", query = "SELECT t FROM TestUnitEntity t WHERE t.isDeleted = :isDeleted")})
public class TestUnitEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "test_unit_id")
    private Integer testUnitId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "test_unit")
    private String testUnit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testUnitId")
    private List<TestEntity> testEntityList;

    public TestUnitEntity() {
    }

    public TestUnitEntity(Integer testUnitId) {
        this.testUnitId = testUnitId;
    }

    public TestUnitEntity(Integer testUnitId, String testUnit, short isDeleted) {
        this.testUnitId = testUnitId;
        this.testUnit = testUnit;
        this.isDeleted = isDeleted;
    }

    public Integer getTestUnitId() {
        return testUnitId;
    }

    public void setTestUnitId(Integer testUnitId) {
        this.testUnitId = testUnitId;
    }

    public String getTestUnit() {
        return testUnit;
    }

    public void setTestUnit(String testUnit) {
        this.testUnit = testUnit;
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
        hash += (testUnitId != null ? testUnitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestUnitEntity)) {
            return false;
        }
        TestUnitEntity other = (TestUnitEntity) object;
        if ((this.testUnitId == null && other.testUnitId != null) || (this.testUnitId != null && !this.testUnitId.equals(other.testUnitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.TestUnitEntity[ testUnitId=" + testUnitId + " ]";
    }
    
}
