package com.example.demo.repository;

import com.example.demo.entity.QuestionTestEntity;
import com.example.demo.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionTestRepository extends JpaRepository<QuestionTestEntity, Integer> {
	public List<QuestionTestEntity> findAll();

	public List<QuestionTestEntity> findAllByTest_Id(Integer id);

	Long countAllByTest(TestEntity id);
	boolean existsByQuestionIdAndTestId(Integer question_id, Integer test_id);
}
