package com.example.demo.service;

import com.example.demo.entity.RoleEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    public List<RoleEntity> findAll();

    public RoleEntity findById(Integer id);

    public Optional<RoleEntity> findByName(String roleName);

    public ResponseEntity<?> deleteById(Integer id);
}
