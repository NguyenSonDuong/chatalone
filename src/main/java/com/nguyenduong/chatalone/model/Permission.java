package com.nguyenduong.chatalone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "permission")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission  extends BaseEntity{

    @Column(name = "`key`", nullable = false, length = 45)
    private String key;

    @Column(name = "name", nullable = false, length = 45)
    private String name;


}