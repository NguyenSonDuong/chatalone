package com.nguyenduong.chatalone.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user_info")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserInfo extends BaseEntity{

    @Column(name = "date_of_birth", nullable = false)
    private  int DateOfBirth;
    @Column(name = "sex", nullable = false)
    private  int sex;

    @Column(name = "quantity_message_user", nullable = false)
    private double quantityMessageUser = 0;

    @Column(name = "quatity_user_request", nullable = false)
    private double quatityUserRequest = 0;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "evaluate_user", joinColumns = {@JoinColumn(name = "id_user")},inverseJoinColumns = {@JoinColumn(name = "id_evaluate")})
    private  Set<Evaluate> evaluates = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_blocker", joinColumns = {@JoinColumn(name = "id_user")}, inverseJoinColumns = {@JoinColumn(name = "user_block")})
    private  Set<Blocker> listBlocker = new HashSet<>();

    public UserInfo(int dateOfBirth, int sex) {
        DateOfBirth = dateOfBirth;
        this.sex = sex;
    }

//    @OneToOne(mappedBy = "userInfo")
//    private User user;
}
