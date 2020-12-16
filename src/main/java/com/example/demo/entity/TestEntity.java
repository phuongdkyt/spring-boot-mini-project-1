package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
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




    @OneToMany(mappedBy = "test")
    private List<UserTestEntity> userTestEntityList=new ArrayList<>();

    @OneToMany(mappedBy = "test")
    private List<QuestionTestEntity> questionTestEntityList=new ArrayList<>();

    @OneToOne(mappedBy = "test")
    private ResultEntity result;
}
