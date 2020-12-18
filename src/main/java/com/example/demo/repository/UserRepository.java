package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findById(Integer id);

    Optional<UserEntity> findByEmail(String email);

    boolean existsById(Integer id);

    boolean existsByEmail(String email);
    List<UserEntity> findByEmailStartsWith(String email);
}
