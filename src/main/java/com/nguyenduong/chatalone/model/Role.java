package com.nguyenduong.chatalone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "role")
@Entity
@Getter
@Setter
public class Role extends BaseEntity {

    public static class RoleKey{
        public static final String USER  = "USER";
        public static final String ADMIN  = "ADMIN";
        public static final String CUSTOMER  = "CUSTOMER";
    }

    @Column(name = "`key`", nullable = false, length = 45)
    private String key;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    public Role(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Role() {
    }

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions = new HashSet<>();


}