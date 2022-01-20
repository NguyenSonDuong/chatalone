package com.nguyenduong.chatalone.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "evaluate")
@Entity
@Getter
@Setter
public class Evaluate extends  BaseEntity{

    @Column(name = "id_user_evaluate",nullable = false)
    private  int idUserEvaluate;

    @Column(name = "point",nullable = false)
    private  double point;

    public Evaluate() {
    }

    public Evaluate(int idUserEvaluate, double point) {
        this.idUserEvaluate = idUserEvaluate;
        this.point = point;
    }

}
