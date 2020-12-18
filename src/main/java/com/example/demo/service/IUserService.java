package com.example.demo.service;

import com.example.demo.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public Optional<UserEntity> findById(Integer id);

    public void deleteById(Integer id);

    public List<UserEntity> findAll();

    public void save(UserEntity userEntities);

    public List<UserEntity> findByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsById(Integer id);
}