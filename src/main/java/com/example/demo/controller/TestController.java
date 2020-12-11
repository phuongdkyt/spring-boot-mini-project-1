package com.example.demo.controller;

import com.example.demo.entity.TestEntity;
import com.example.demo.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/testlist")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping
    //hiển thị danh sách bài thi
    public List<TestEntity> findAll() {
        return testService.findAll();
    }

    @GetMapping("/{id}")
    //tìm kiếm bài thi theo id
    public Optional<TestEntity> findById(@PathVariable Integer id) {
        return testService.findById(id);
    }

    @PostMapping("/savetest")
    //tạo bài thi
    public ResponseEntity<?> saveAll(@RequestBody List<TestEntity> testEntities) {
        return testService.saveAll(testEntities);
    }

    //tìm kiếm bài thi theo tên bài thi
    @GetMapping("search/{name}")
    public ResponseEntity<Optional<TestEntity>> findByName(@PathVariable(value = "name") String name) {
        Optional<TestEntity> result = testService.findByName(name);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTestEntity(@PathVariable Integer id) {
        return testService.deleteTestById(id);
    }
}