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
@Table(name = "role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoleEntity.findAll", query = "SELECT r FROM RoleEntity r"),
    @NamedQuery(name = "RoleEntity.findByRoleId", query = "SELECT r FROM RoleEntity r WHERE r.roleId = :roleId"),
    @NamedQuery(name = "RoleEntity.findByRoleName", query = "SELECT r FROM RoleEntity r WHERE r.roleName = :roleName"),
    @NamedQuery(name = "RoleEntity.findByIsDeleted", query = "SELECT r FROM RoleEntity r WHERE r.isDeleted = :isDeleted")})
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_id")
    private Integer roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "role_name")
    private String roleName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId")
    private List<UserRoleEntity> userRoleEntityList;

    public RoleEntity() {
    }

    public RoleEntity(Integer roleId) {
        this.roleId = roleId;
    }

    public RoleEntity(Integer roleId, String roleName, short isDeleted) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isDeleted = isDeleted;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<UserRoleEntity> getUserRoleEntityList() {
        return userRoleEntityList;
    }

    public void setUserRoleEntityList(List<UserRoleEntity> userRoleEntityList) {
        this.userRoleEntityList = userRoleEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleEntity)) {
            return false;
        }
        RoleEntity other = (RoleEntity) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.RoleEntity[ roleId=" + roleId + " ]";
    }
    
}
