package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    public List<UserEntity> findAll();

    public ResponseEntity<?> save(UserEntity user);

    public UserEntity findById(Integer id);

    public ResponseEntity<?> deleteById(Integer id);
}
