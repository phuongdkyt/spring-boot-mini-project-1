package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_test_users")
public class UserTestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "test_id")
	private TestEntity test;

	@Column(columnDefinition = "integer default 0")
	private Integer status_result;
	@Column(columnDefinition = "integer default 0")
	private Integer status_notice;

//   @OneToOne(mappedBy = "userTest")
//   private ResultEntity result;
}
