package com.nguyenduong.chatalone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user")
@Entity
@Getter
@Setter
public class User  extends BaseEntity {

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userinfo", referencedColumnName = "id")
    private  UserInfo userInfo = new UserInfo();

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();
}