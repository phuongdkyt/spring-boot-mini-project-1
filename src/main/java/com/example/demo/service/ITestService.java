package com.example.demo.service;

import com.example.demo.entity.TestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ITestService {
    ResponseEntity<?> findAll();
    Optional<TestEntity> findById(Integer id);
    ResponseEntity<?> saveAll(List<TestEntity> testEntities) ;
    Optional<TestEntity> findByName(String name);
    ResponseEntity<?> deleteTestById(Integer id);
    ResponseEntity<?> updateTest(TestEntity testEntity, Integer id);
    String  addListTestWithUser(List<Integer> idListUserRequest, Integer testId);
    String addListQuestionsWithTest(List<Integer> idListQuestionsTest, Integer testId);

}
