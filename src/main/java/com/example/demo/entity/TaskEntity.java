package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;


    @Column(nullable = false)
    private String taskAwnser;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_test_id", referencedColumnName = "id")
    private UserTestEntity userTest;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "question_test_id", referencedColumnName = "id")
    private QuestionTestEntity questionTest;
}
