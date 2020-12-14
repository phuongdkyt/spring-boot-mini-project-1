package com.example.demo.service.impl;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<QuestionEntity> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<QuestionEntity> findById(Integer id) {
        return questionRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        questionRepository.deleteById(id);
    }

    @Override
    public void save(QuestionEntity question, Integer id) {
        Optional<QuestionEntity> questionCurrent = questionRepository.findById(id);
//        QuestionEntity questionEntity;
        if (questionCurrent.isPresent()) {

            questionCurrent.get().setA(question.getA());
            questionCurrent.get().setB(question.getB());
            questionCurrent.get().setC(question.getC());
            questionCurrent.get().setD(question.getD());
            questionCurrent.get().setAnswer(question.getAnswer());
            questionCurrent.get().setQuestion(question.getQuestion());
            questionRepository.save(questionCurrent.get());
        }

    }

    @Override
    public void saveAll(List<QuestionEntity> questionEntityList, Character lv) {

        Optional<UserEntity> userEntity = userRepository.findById(1);

        for (int i = 0; i < questionEntityList.size(); i++) {
            questionEntityList.get(i).setUser(userEntity.get());
            questionEntityList.get(i).setLevel(lv);
            if (Common.isNullOrEmpty(questionEntityList.get(i).getAnswer()))
                questionEntityList.get(i).setQuestionType(Constants.TL);
            else
                questionEntityList.get(i).setQuestionType(Constants.TN);
        }
        questionRepository.saveAll(questionEntityList);
    }
}
