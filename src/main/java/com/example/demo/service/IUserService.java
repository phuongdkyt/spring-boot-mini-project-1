package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    public ResponseEntity<?> findAll();

    public ResponseEntity<?> save(UserEntity user);

    public ResponseEntity<?> findById(Integer id);

    public ResponseEntity<?> deleteById(Integer id);
}
