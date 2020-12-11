package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "question",unique = true)
    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;
    private Character level;
    private String questionType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "question")
    List<TaskEntity> taskEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    List<QuestionTestEntity> questionTestEntityList = new ArrayList<>();




}
