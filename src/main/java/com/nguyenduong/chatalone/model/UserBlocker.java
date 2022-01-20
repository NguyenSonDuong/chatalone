package com.nguyenduong.chatalone.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "user_blocker")
@Entity
@Getter
@Setter
public class UserBlocker {

    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_block", referencedColumnName = "idUserBlock")
    private Blocker user_block_id;

}
