package com.nguyenduong.chatalone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Date;

@Table(name = "user_info")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fullname", length = 45)
    private String fullname;

    @Column(name = "sex")
    private int sex;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "hobit", length = 45)
    private String hobit;

}