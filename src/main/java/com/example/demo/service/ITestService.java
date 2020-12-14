package com.example.demo.service;

import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.TestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ITestService {
    public ResponseEntity<?> findAll();
    public Optional<TestEntity> findById(Integer id);
    public ResponseEntity<?> saveAll(List<TestEntity> testEntities) ;
    Optional<TestEntity> findByName(String name);
    public ResponseEntity<?> deleteTestById(Integer id);
    public ResponseEntity<?> updateTest(TestEntity testEntity, Integer id);
}
