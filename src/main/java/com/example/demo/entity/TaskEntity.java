package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_tasks")
public class TaskEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;



	private String answer;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "question_test_id")
	private QuestionTestEntity questionTest;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "test_id")
	private TestEntity test;

	private Double mark;

}
