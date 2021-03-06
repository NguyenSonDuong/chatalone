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
    @Column(name = "id_user", nullable = false)
    private Integer userId;


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_block", referencedColumnName = "id")
    private Blocker user_block_id = new Blocker();

}
