package com.example.demo.service;

import com.example.demo.entity.NoticeOutput;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.payload.request.EssayScoringRequest;

import java.util.List;

public interface ITaskService {
	public String sendAnswer(List<QuestionEntity> questionEntityList, String testName);

	public NoticeOutput getNotice(Integer userId, Integer testId);

	String essayScoring(List<EssayScoringRequest> essayScoringRequestList, String testName, Integer userId);

	String getMultipleChoiceScores(Integer id, String testName);

	String getMarkOnTotalQuestion(String testName);

	String getEssayScoreResults(String testName);
}

