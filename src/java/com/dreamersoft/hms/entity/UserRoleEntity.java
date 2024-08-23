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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aizaz
 */
@Entity
@Table(name = "user_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRoleEntity.findAll", query = "SELECT u FROM UserRoleEntity u"),
    @NamedQuery(name = "UserRoleEntity.findByUserRoleId", query = "SELECT u FROM UserRoleEntity u WHERE u.userRoleId = :userRoleId"),
    @NamedQuery(name = "UserRoleEntity.findByIsDeleted", query = "SELECT u FROM UserRoleEntity u WHERE u.isDeleted = :isDeleted")})
public class UserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_role_id")
    private Integer userRoleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private RoleEntity roleId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UsersEntity userId;

    public UserRoleEntity() {
    }

    public UserRoleEntity(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public UserRoleEntity(Integer userRoleId, short isDeleted) {
        this.userRoleId = userRoleId;
        this.isDeleted = isDeleted;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public RoleEntity getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleEntity roleId) {
        this.roleId = roleId;
    }

    public UsersEntity getUserId() {
        return userId;
    }

    public void setUserId(UsersEntity userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleId != null ? userRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoleEntity)) {
            return false;
        }
        UserRoleEntity other = (UserRoleEntity) object;
        if ((this.userRoleId == null && other.userRoleId != null) || (this.userRoleId != null && !this.userRoleId.equals(other.userRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.UserRoleEntity[ userRoleId=" + userRoleId + " ]";
    }
    
}
