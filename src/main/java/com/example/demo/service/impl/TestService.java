package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.*;
import com.example.demo.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService implements ITestService {
	@Autowired
	TestRepository testRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	UserTestRepository userTestRepository;
	@Autowired
	QuestionTestRepository questionTestRepository;

	@Override
	public List<TestEntity> findAll() {
		return testRepository.findAll();
	}

	@Override
	public Optional<TestEntity> findById(Integer id) {
		return testRepository.findById(id);
	}

	@Override
	public ResponseEntity<?> save(TestEntity testEntity) {
		testRepository.save(testEntity);
		return new ResponseEntity("Tạo bài thi thành công", HttpStatus.OK);
	}

	@Override
	public Optional<TestEntity> findByName(String name) {
		return testRepository.findByName(name);
	}

	@Override
	public ResponseEntity<?> deleteTestById(Integer id) {
		testRepository.deleteById(id);
		return new ResponseEntity(new MessageResponse(true, "Xóa thành công"), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateTest(TestEntity testEntity, Integer id) {
		Optional<TestEntity> updateTest = testRepository.findById(id);
		if (updateTest.isPresent()) {
//            updateTest.get().setUser(testEntity.getUser());
			updateTest.get().setTestName(testEntity.getTestName());
			updateTest.get().setTestDateBegin(testEntity.getTestDateBegin());
			updateTest.get().setTestDateFinish(testEntity.getTestDateFinish());
			testRepository.save(updateTest.get());
		}
		return new ResponseEntity(new MessageResponse(true, "Cập nhật thành công"), HttpStatus.OK);
	}

	//them user theo bai test
	@Override
	public String addListTestWithUser(List<Integer> idListUserRequest, Integer testId) {
		Optional<TestEntity> testEntity = testRepository.findById(testId);
		for (int i = 0; i < idListUserRequest.size(); i++) {
			UserTestEntity userTestEntity = new UserTestEntity();
			userTestEntity.setTest(testEntity.get());
			Optional<UserEntity> userEntity = userRepository.findById(idListUserRequest.get(i));
			userTestEntity.setUser(userEntity.get());
			userTestRepository.save(userTestEntity);
		}
		return "đã thêm thành công";
	}

	//them cau hoi theo bai test
	@Override
	public String addListQuestionsWithTest(List<Integer> idListQuestionsTest, Integer testId) {
		Optional<TestEntity> testEntity = testRepository.findById(testId);

		for (int i = 0; i < idListQuestionsTest.size(); i++) {
			if (!questionTestRepository.existsByQuestionIdAndTestId(idListQuestionsTest.get(i), testId)) {
				QuestionTestEntity questionTestEntity = new QuestionTestEntity();
				questionTestEntity.setTest(testEntity.get());
				Optional<QuestionEntity> questionEntity = questionRepository.findById(idListQuestionsTest.get(i));
				questionTestEntity.setQuestion(questionEntity.get());
				questionTestRepository.save(questionTestEntity);
			}
		}

		return "đã cập nhật thành công";
	}


}
