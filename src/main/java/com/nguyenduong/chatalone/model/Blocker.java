package com.nguyenduong.chatalone.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "blocker")
@Entity
@Getter
@Setter
public class Blocker extends BaseEntity{

    @Column(name = "idUserBlock", nullable = false)
    private  int idUserBlock;

    @Column(name = "name", nullable = false)
    private  String name ;

    @Column(name = "create_at", nullable = false)
    private Date create_at;


    public Blocker(int idUserBlock) {
        this.idUserBlock = idUserBlock;
    }

    public Blocker(int idUserBlock, String name, Date create_at) {
        this.idUserBlock = idUserBlock;
        this.name = name;
        this.create_at = create_at;
    }

    public Blocker() {
    }



}
