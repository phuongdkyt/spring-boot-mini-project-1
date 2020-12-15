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
    private  Integer status_result;
    private  Integer status_notice;

    @Column(nullable = false)
    private String taskAwnser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_test_id")
    private QuestionTestEntity questionTest;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(mappedBy = "task")
    private ResultEntity result;

}
