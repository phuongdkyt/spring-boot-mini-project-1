package com.example.demo.service;

import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.TaskEntity;
import com.example.demo.payload.request.ResultEssayRequest;

import java.util.List;

public interface ITaskService {
    public String sendAnswer(List<QuestionEntity> questionEntityList,String testName);

}

