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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersEntity.findAll", query = "SELECT u FROM UsersEntity u"),
    @NamedQuery(name = "UsersEntity.findByUserId", query = "SELECT u FROM UsersEntity u WHERE u.userId = :userId"),
    @NamedQuery(name = "UsersEntity.findByUserName", query = "SELECT u FROM UsersEntity u WHERE u.userName = :userName"),
    @NamedQuery(name = "UsersEntity.findByUserAddress", query = "SELECT u FROM UsersEntity u WHERE u.userAddress = :userAddress"),
    @NamedQuery(name = "UsersEntity.findByUserPhone", query = "SELECT u FROM UsersEntity u WHERE u.userPhone = :userPhone"),
    @NamedQuery(name = "UsersEntity.findByUserEmail", query = "SELECT u FROM UsersEntity u WHERE u.userEmail = :userEmail"),
    @NamedQuery(name = "UsersEntity.findByUserPassword", query = "SELECT u FROM UsersEntity u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "UsersEntity.findByIsDeleted", query = "SELECT u FROM UsersEntity u WHERE u.isDeleted = :isDeleted")})
public class UsersEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_address")
    private String userAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_phone")
    private String userPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_email")
    private String userEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_password")
    private String userPassword;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<DoctorEntity> doctorEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<UserRoleEntity> userRoleEntityList;

    public UsersEntity() {
    }

    public UsersEntity(Integer userId) {
        this.userId = userId;
    }

    public UsersEntity(Integer userId, String userName, String userAddress, String userPhone, String userEmail, String userPassword, short isDeleted) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.isDeleted = isDeleted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<DoctorEntity> getDoctorEntityList() {
        return doctorEntityList;
    }

    public void setDoctorEntityList(List<DoctorEntity> doctorEntityList) {
        this.doctorEntityList = doctorEntityList;
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
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersEntity)) {
            return false;
        }
        UsersEntity other = (UsersEntity) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dreamersoft.hms.entity.UsersEntity[ userId=" + userId + " ]";
    }
    
}
