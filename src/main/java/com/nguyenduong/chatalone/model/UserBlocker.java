package com.nguyenduong.chatalone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_blocker")
@Getter
@Setter
public class UserBlocker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_userblocker", nullable = false)
    private Integer idBlocker;

    @Column(name = "create_at", nullable = false)
    private Date createAt;


    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    public UserBlocker(Integer idBlocker, Date createAt) {
        this.idBlocker = idBlocker;
        this.createAt = createAt;
    }

    public UserBlocker() {
    }

    @OneToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;


}