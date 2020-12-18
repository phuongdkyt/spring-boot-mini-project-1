package com.example.demo.repository;

import com.example.demo.entity.TestEntity;
import com.example.demo.entity.UserTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTestRepository extends JpaRepository<UserTestEntity,Integer> {
            List<UserTestEntity> findAllByUser_Id(Integer id);

}
