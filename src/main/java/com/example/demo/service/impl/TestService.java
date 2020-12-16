package com.example.demo.service.impl;

import com.example.demo.entity.TestEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.TestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class TestService implements ITestService {
    @Autowired
    TestRepository testRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public ResponseEntity<?> findAll() {
        HashMap<String,List<TestEntity>> list =new HashMap<>();
        list.put("result",testRepository.findAll());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
    }

    @Override
    public Optional<TestEntity> findById(Integer id) {
        return testRepository.findById(id);
    }

    @Override
    public ResponseEntity<?> saveAll(List<TestEntity> testEntities) {
//        Optional<UserEntity> userEntity=userRepository.findById(1);
//        for (int i=0;i<testEntities.size();i++){
//            testEntities.get(i).setUser(userEntity.get());
//        }
//        testRepository.saveAll(testEntities);
        return  new ResponseEntity("Tạo bài thi thành công", HttpStatus.OK);
    }

    @Override
    public Optional<TestEntity> findByName(String name) {
        return testRepository.findByName(name);
    }

    @Override
    public ResponseEntity<?> deleteTestById(Integer id) {
        testRepository.deleteById(id);
        return new ResponseEntity(new MessageResponse(true, "Xóa thành công"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateTest(TestEntity testEntity, Integer id) {
        Optional<TestEntity> updateTest=testRepository.findById(id);
        if(updateTest.isPresent()){
//            updateTest.get().setUser(testEntity.getUser());
            updateTest.get().setTestTime(testEntity.getTestTime());
            updateTest.get().setTestDate(testEntity.getTestDate());
            updateTest.get().setTestName(testEntity.getTestName());
            testRepository.save(updateTest.get());
        }
        return new ResponseEntity(new MessageResponse(true,"Cập nhật thành công"),HttpStatus.OK);
    }


}
