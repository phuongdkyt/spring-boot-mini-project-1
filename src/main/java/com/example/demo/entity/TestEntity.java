package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
	@Column(nullable = false, unique = true)
	private String testName;
	@Column(nullable = false)
	private Integer testTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date testDateBegin;
	@Temporal(TemporalType.TIMESTAMP)
	private Date testDateFinish;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
	private List<UserTestEntity> userTestEntityList = new ArrayList<>();

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
	private List<QuestionTestEntity> questionTestEntityList = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
	private List<TaskEntity> taskEntityList = new ArrayList<>();

}
