package com.example.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_results")
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @OneToOne(cascade = CascadeType.ALL, optional = false)
//    @JoinColumn(name = "user_test_id", referencedColumnName = "id")
//    private UserTestEntity userTest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private TestEntity test;

    private  Integer status_result;
    private  Integer status_notice;
    private String multipleChoiceAnswers;
    private String essayAnswers;

}
