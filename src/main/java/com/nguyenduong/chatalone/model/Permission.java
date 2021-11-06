package com.nguyenduong.chatalone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "permission")
@Entity
@Getter
@Setter
public class Permission  extends BaseEntity{

    @Column(name = "`key`", nullable = false, length = 45)
    private String key;

    @Column(name = "name", nullable = false, length = 45)
    private String name;


}