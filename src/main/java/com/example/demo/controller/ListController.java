package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.TestEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.service.IListService;
import com.example.demo.service.ITaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

// Controller dành cho Học viên
@RestController
@RequestMapping("api/lists")
public class ListController {
	@Autowired
	IListService listService;
	@Autowired
	ITaskService taskService;
	Logger logger = Logger.getLogger("ListController");
	long timeStamp;
	BaseMessage response;

	@GetMapping
	public ResponseEntity<?> findAll() {
		timeStamp = Common.getTimeStamp();
		try {
			return listService.findAllByUserId(Common.getUserId());
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@GetMapping("/{id}")
	//Lấy điểm theo bài thi
	public ResponseEntity<?> findById(@Valid @PathVariable Integer id) {
		timeStamp = Common.getTimeStamp();
		try {
			return listService.getMarkOnTotalQuestion(id);
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "findById"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}
