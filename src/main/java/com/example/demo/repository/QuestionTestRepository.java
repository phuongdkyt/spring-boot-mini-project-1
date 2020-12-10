package com.example.demo.repository;

import com.example.demo.entity.QuestionTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTestRepository extends JpaRepository<QuestionTestEntity,Integer> {
}
