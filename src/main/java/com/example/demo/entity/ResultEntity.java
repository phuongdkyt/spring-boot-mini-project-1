package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_results")
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_test_id", referencedColumnName = "id")
    private UserTestEntity userTest;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private TaskEntity task;

    private String multipleChoiceAnswers;
    private String essayAnswers;

}
