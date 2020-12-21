package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.TestEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.service.ITestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tests")

public class TestController {
	@Autowired
	ITestService testService;
	Logger logger = Logger.getLogger("TestController");
	long timeStamp;
	BaseMessage response;

	//hiển thị danh sách bài thi
	@GetMapping
	public ResponseEntity<?> findAll() {
		timeStamp = Common.getTimeStamp();
		try {
			List<TestEntity> lst = testService.findAll();
			if (Common.isNullOrEmpty(lst)) {
				//sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không có bài thi nào!", timeStamp);
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
	//tìm kiếm bài thi theo id
	public Optional<TestEntity> findById(@PathVariable Integer id) {
		return testService.findById(id);
	}

	@PostMapping
	//tạo bài thi
	public ResponseEntity<?> saveAll(@RequestBody List<TestEntity> testEntities) {
		return testService.saveAll(testEntities);
	}

	//tìm kiếm bài thi theo tên bài thi
	@GetMapping("search/{name}")
	public ResponseEntity<Optional<TestEntity>> findByName(@PathVariable(value = "name") String name) {
		Optional<TestEntity> result = testService.findByName(name);
		return ResponseEntity.ok().body(result);
	}

	//xóa bài thi
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTestEntity(@PathVariable Integer id) {
		return testService.deleteTestById(id);
	}

	//update bài thi
	@PostMapping("/{id}")
	public ResponseEntity<?> updateTest(@RequestBody TestEntity testEntity, @PathVariable Integer id) {
		return testService.updateTest(testEntity, id);
	}

	@PostMapping("/{id}/users")
	public ResponseEntity<?> addListUserWithTest(@RequestBody List<Integer> idListUserRequests, @PathVariable Integer id) {
		timeStamp = Common.getTimeStamp();
		try {
			String results = testService.addListTestWithUser(idListUserRequests, id);

			response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, results);
			logger.info(Common.createMessageLog(idListUserRequests, response, Common.getUserName(), timeStamp, "getMultipleChoiceScores"));
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
			logger.error(Common.createMessageLog(idListUserRequests, response, Common.getUserName(), timeStamp, "getMultipleChoiceScores"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PostMapping("/{id}/tests")
	public ResponseEntity<?> addListQuestionsWithTest(@RequestBody List<Integer> idListQuestion, @PathVariable Integer id) {
		timeStamp = Common.getTimeStamp();
		try {
			String results = testService.addListQuestionsWithTest(idListQuestion, id);

			response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, results);
			logger.info(Common.createMessageLog(idListQuestion, response, Common.getUserName(), timeStamp, "getMultipleChoiceScores"));
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
			logger.error(Common.createMessageLog(idListQuestion, response, Common.getUserName(), timeStamp, "getMultipleChoiceScores"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}


}