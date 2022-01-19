package com.nguyenduong.chatalone.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "blocker")
@Entity
@Getter
@Setter
public class Blocker extends BaseEntity{

    @Column(name = "idUserBlock", nullable = false)
    private  int idUserBlock;

    @Column(name = "idUser", nullable = false)
    private  int idUser;


    public Blocker(int idUserBlock) {
        this.idUserBlock = idUserBlock;
    }

    public Blocker() {
    }



}
