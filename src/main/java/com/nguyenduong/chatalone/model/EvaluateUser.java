package com.nguyenduong.chatalone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "evaluate_user")
@Entity
@Getter
@Setter
public class EvaluateUser {

    @Id
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_evaluate",referencedColumnName = "id")
    private Evaluate evaluate = new Evaluate();
}
