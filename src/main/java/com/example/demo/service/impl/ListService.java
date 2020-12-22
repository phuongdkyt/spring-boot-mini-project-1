package com.example.demo.service.impl;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.TestEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TestRepository;
import com.example.demo.service.IListService;
import com.example.demo.service.ITaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ListService implements IListService {
	Logger logger = Logger.getLogger("ListService");
	BaseMessage response;
	long timeStamp;
	HashMap<String, Object> result;
	@Autowired
	TestRepository testRepository;
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	ITaskService taskService;


	@Override
	public ResponseEntity<?> findAllByUserId(Integer userId) {
		timeStamp = Common.getTimeStamp();
		try {
			List<TestEntity> testEntities = testRepository.findByUserId(userId);
			if (Common.isNullOrEmpty(testEntities)) {
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không có lịch thi!", timeStamp);
				logger.error(Common.createMessageLog(userId, response, Common.getUserName(), timeStamp, "findAllByUserId"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} else {
				response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, testEntities);
				logger.error(Common.createMessageLog(userId, response, Common.getUserName(), timeStamp, "findAllByUserId"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAllByUserId"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<?> getMarkOnTotalQuestion(Integer testId) {
		timeStamp = Common.getTimeStamp();
		result = new HashMap<String, Object>();
		try {
			String scoreEssay = taskService.getEssayScoreResults(testId);
			String scoreMultipleChoice = taskService.getMultipleChoiceScores(testId);

			if (Common.isNullOrEmpty(scoreEssay) && Common.isNullOrEmpty(scoreMultipleChoice)) {
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Bạn chưa làm bài thi!", timeStamp);
				logger.error(Common.createMessageLog(testId, response, Common.getUserName(), timeStamp, "getMarkOnTotalQuestion"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} else {
				result.put("scoreEssay", scoreEssay);
				result.put("scoreMultipleChoice", scoreMultipleChoice);

				response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, result);
				logger.error(Common.createMessageLog(testId, response, Common.getUserName(), timeStamp, "getMarkOnTotalQuestion"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(testId, response, Common.getUserName(), timeStamp, "getMarkOnTotalQuestion"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}
