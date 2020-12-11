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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "test_id")
    TestEntity test;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    QuestionEntity question;

    @Column(nullable = false)
    private String taskAwnser;

}
