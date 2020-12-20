package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.service.IQuestionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/questions")
public class QuestionController {
	@Autowired
	IQuestionService questionService;
	Logger logger = Logger.getLogger("QuestionController");
	long timeStamp;
	BaseMessage response;

	@GetMapping
	public ResponseEntity<?> findAll() {
		timeStamp = Common.getTimeStamp();
		try {
			List<QuestionEntity> lst = questionService.findAll();
			if (Common.isNullOrEmpty(lst)) {
				//sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không có câu hỏi nào!", timeStamp);
				logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} else {
				//tham chiếu đến đối tượng cần trả về
				response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, lst);
				logger.info(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@Valid @PathVariable Integer id) {
		timeStamp = Common.getTimeStamp();
		try {
			Optional<QuestionEntity> questionEntity = questionService.findById(id);
			response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, questionEntity.get());
			logger.info(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "findbyId"));
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy id câu hỏi", timeStamp);
			logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "findByid"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@GetMapping("/findbyname/{question}")
	public ResponseEntity<?> findByQuestion(@Valid @PathVariable String question) {
		timeStamp = Common.getTimeStamp();
		try {
			List<QuestionEntity> questionEntityList = questionService.findByQuestion(question);
			response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, questionEntityList);
			logger.info(Common.createMessageLog(question, response, Common.getUserName(), timeStamp, "findByQuestion"));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy câu hỏi nào!", timeStamp);
			logger.error(Common.createMessageLog(question, response, Common.getUserName(), timeStamp, "findByQuestion"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody QuestionEntity question) {
		timeStamp = Common.getTimeStamp();
		try {
			response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Thêm thành công", timeStamp);
			questionService.save(question);
			logger.info(Common.createMessageLog(question, response, Common.getUserName(), timeStamp, "save"));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Không thể thêm câu hỏi", timeStamp);
			logger.error(Common.createMessageLog(question, response, Common.getUserName(), timeStamp, "save"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody QuestionEntity question, @Valid @PathVariable Integer id) {
		timeStamp = Common.getTimeStamp();
		try {
			Optional<QuestionEntity> questionEntity = questionService.findById(id);
			response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Sửa Thành công", timeStamp);
			questionService.update(question, id);
			logger.info(Common.createMessageLog(question, response, Common.getUserName(), timeStamp, "update"));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "không tìm thấy id câu hỏi", timeStamp);
			logger.error(Common.createMessageLog(question, response, Common.getUserName(), timeStamp, "update"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PostMapping("/saveall/{level}")
	public ResponseEntity<?> saveAll(@Valid @RequestBody List<QuestionEntity> questions,
	                                 @Valid @PathVariable Character level) {
		timeStamp = Common.getTimeStamp();

		try {
			if (Common.isNullOrEmpty(questions)) {
				response = new BaseMessage(Constants.ERROR_RESPONSE, "không thêm các câu hỏi được", timeStamp);
				logger.error(Common.createMessageLog(questions, response, Common.getUserName(), timeStamp, "saveall"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} else {
				questionService.saveAll(questions, level);
				response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Thêm danh sách Thành công", timeStamp);
				logger.info(Common.createMessageLog(questions, response, Common.getUserName(), timeStamp, "saveall"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}

		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(questions, response, Common.getUserName(), timeStamp, "saveall"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		timeStamp = Common.getTimeStamp();
		try {
			Optional<QuestionEntity> questionEntity = questionService.findById(id);
			response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Xóa thành công", timeStamp);
			questionService.deleteById(id);
			logger.info(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "deleteById"));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy id câu hỏi", timeStamp);
			logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "deleteById"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
