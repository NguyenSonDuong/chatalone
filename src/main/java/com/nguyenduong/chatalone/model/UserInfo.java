package com.nguyenduong.chatalone.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    public UserInfo(int dateOfBirth, int sex) {
        DateOfBirth = dateOfBirth;
        this.sex = sex;
    }

//    @OneToOne(mappedBy = "userInfo")
//    private User user;
}
