package com.nguyenduong.chatalone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Date;

@Table(name = "token")
@Entity
@Getter
@Setter
public class Token extends BaseEntity {

    @Column(name = "token", length = 1000)
    private String token;

    @Column(name = "token_exp_date")
    private Date tokenExpDate;

}