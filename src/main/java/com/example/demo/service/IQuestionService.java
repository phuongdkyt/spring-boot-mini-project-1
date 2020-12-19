package com.example.demo.service;

import com.example.demo.entity.QuestionEntity;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
	public List<QuestionEntity> findAll();

	public Optional<QuestionEntity> findById(Integer id);

	public List<QuestionEntity> findByQuestion(String name);

	public void deleteById(Integer id);

	public void save(QuestionEntity question, Integer id);

	public void saveAll(List<QuestionEntity> questionEntityList, Character lv);

}
