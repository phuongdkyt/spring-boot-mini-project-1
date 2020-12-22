package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_test_questions")
public class QuestionTestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
