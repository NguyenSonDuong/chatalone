package com.nguyenduong.chatalone.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "rank", nullable = false)
    private double rank;

    @Column(name = "quatity_user", nullable = false)
    private double QuatityUser;

    @OneToMany(mappedBy = "userInfo")
    private List<UserBlocker> userBlockers = new ArrayList<>();

    public UserInfo(int dateOfBirth, int sex) {
        DateOfBirth = dateOfBirth;
        this.sex = sex;
    }

//    @OneToOne(mappedBy = "userInfo")
//    private User user;
}
