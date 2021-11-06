package com.nguyenduong.chatalone.model;

import javax.persistence.*;

@Table(name = "user_role")
@Entity
public class UserRole {
    private static final long serialVersionUID = -37924105558227848L;

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole() {
    }

    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
}