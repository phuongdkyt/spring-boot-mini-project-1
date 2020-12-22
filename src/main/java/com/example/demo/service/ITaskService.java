package com.example.demo.service;

import com.example.demo.entity.NoticeOutput;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.TaskEntity;
import com.example.demo.payload.request.EssayScoringRequest;

import java.util.List;
import java.util.Map;

public interface ITaskService {
	public String sendAnswer(List<QuestionEntity> questionEntityList, Integer test_id);

	public NoticeOutput getNotice(Integer userId, Integer testId);

	String essayScoring(List<EssayScoringRequest> essayScoringRequestList, Integer test_id, Integer userId);

	String getMultipleChoiceScores( Integer test_id);

	String getMarkOnTotalQuestion(Integer test_id);

	String getEssayScoreResults(Integer test_id);

	List<Map<String,Object>> findAllByTask(Integer user_id, Integer test_id);
}

