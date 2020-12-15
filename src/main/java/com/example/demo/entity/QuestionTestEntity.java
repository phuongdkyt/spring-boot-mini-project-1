package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_test_questions")
public class QuestionTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;


    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @JsonIgnore
    @OneToMany(mappedBy = "questionTest")
    private List<TaskEntity> taskEntityList;
}
