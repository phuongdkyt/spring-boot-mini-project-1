package com.example.demo.repository;

import com.example.demo.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<ResultEntity,Integer> {
}
