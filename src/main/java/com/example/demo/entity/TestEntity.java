package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_tests")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String testName;
    @Column(nullable = false)
    private Integer testTime;
    @Temporal(TemporalType.DATE)
    private Date testDate;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @OneToMany(mappedBy = "test")
    private List<UserTestEntity> userTestEntityList=new ArrayList<>();

    @OneToMany(mappedBy = "test")
    private List<QuestionTestEntity> questionTestEntityList=new ArrayList<>();

}
