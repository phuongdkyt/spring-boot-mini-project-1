package com.example.demo.service;

import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.TaskEntity;
import com.example.demo.entity.TestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ITestService {
	public List<TestEntity> findAll();

	public Optional<TestEntity> findById(Integer id);

	public ResponseEntity<?> save(TestEntity testEntity);

	public Optional<TestEntity> findByName(String name);

	public ResponseEntity<?> deleteTestById(Integer id);

	public ResponseEntity<?> updateTest(TestEntity testEntity, Integer id);

	public String addListTestWithUser(List<Integer> idListUserRequest, Integer testId);

	public String addListQuestionsWithTest(List<Integer> idListQuestionsTest, Integer testId);



	List<TestEntity> getAllTestAlready();

}
