package com.nguyenduong.chatalone.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
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

    @JsonIgnore(value = false)
    @Column(name = "password", nullable = false, length = 150)
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userinfo", referencedColumnName = "id")
    private  UserInfo userInfo = new UserInfo();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}