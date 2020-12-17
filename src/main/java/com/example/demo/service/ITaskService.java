package com.example.demo.service;

import com.example.demo.entity.NoticeOutput;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.TaskEntity;
import com.example.demo.payload.request.EssayScoringRequest;

import java.util.List;

public interface ITaskService {
    public String sendAnswer(List<QuestionEntity> questionEntityList,String testName);
    public NoticeOutput getNotice(Integer userId, Integer testId );

    String essayScoring(List<EssayScoringRequest> essayScoringRequestList);
    String getMultipleChoiceScores();
    Object getMarkOnTotalQuestion(String testName);
    Long getEssayScoreResults(String testName);
}

