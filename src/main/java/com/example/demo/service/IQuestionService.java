package com.example.demo.service;

import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    public ResponseEntity<?> findAll();
    public Optional<QuestionEntity> findById(Integer id);
    public ResponseEntity<?> deleteById(Integer id);
    public ResponseEntity<?> save(QuestionEntity question,Integer id);
    public ResponseEntity<?> saveAll(List<QuestionEntity> questionEntityList,Character lv) ;

}
