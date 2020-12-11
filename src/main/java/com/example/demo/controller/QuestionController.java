package com.example.demo.controller;

import com.example.demo.entity.QuestionEntity;
import com.example.demo.service.impl.QuestionService;
import org.apache.catalina.startup.ClassLoaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping
    public ResponseEntity<?> findAll(){
        return questionService.findAll();
    }
    @GetMapping("/{id}")
    public Optional<QuestionEntity> findById(@PathVariable Integer id){
        return  questionService.findById(id);
    }
    @PostMapping("/save/{id}")
    public ResponseEntity<?> save(@RequestBody QuestionEntity question ,@PathVariable Integer id){
      return   questionService.save(question,id);

    }
    @PostMapping("/saveall/{lv}")
    public ResponseEntity<?> saveAll(@RequestBody List<QuestionEntity> questions ,@PathVariable Character lv){
        return   questionService.saveAll(questions,lv);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        return  questionService.deleteById(id);
    }

}
