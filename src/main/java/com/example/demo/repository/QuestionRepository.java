package com.example.demo.repository;


import com.example.demo.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
	public Optional<QuestionEntity> findById(Integer id);

	public List<QuestionEntity> findByQuestionStartsWith(String name);
}
