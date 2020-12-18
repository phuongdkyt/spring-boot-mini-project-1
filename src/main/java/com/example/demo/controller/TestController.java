package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.TestEntity;
import com.example.demo.entity.UserTestEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.repository.UserTestRepository;
import com.example.demo.service.impl.TestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tests")

public class TestController {
    @Autowired
    TestService testService;
    Logger logger = Logger.getLogger("TestController");

    @GetMapping("list")
    //hiển thị danh sách bài thi
    public ResponseEntity<?> findAll() {
        return testService.findAll();
    }

    @GetMapping("list/{id}")
    //tìm kiếm bài thi theo id
    public Optional<TestEntity> findById(@PathVariable Integer id) {
        return testService.findById(id);
    }

    @PostMapping("/savetest")
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
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateTest(@RequestBody TestEntity testEntity ,@PathVariable Integer id){
        return testService.updateTest(testEntity,id);
    }

//    @Autowired
//    UserTestRepository userTestRepository;
//    @GetMapping("/user/{id}")
//    public List<UserTestEntity> gogogo(@PathVariable Integer id){
//        return  userTestRepository.findAllByUser_Id(id);
//    }
    @PostMapping("/add/{id_test}/users")
    public BaseMessage addListUserWithTest(@RequestBody List<Integer> idListUserRequests, @PathVariable Integer id_test){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            String results=testService.addListTestWithUser(idListUserRequests,id_test);

            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, results);
            logger.info(Common.createMessageLog(idListUserRequests, response, Common.getUserName(), timeStamp, "getMultipleChoiceScores"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(idListUserRequests, response, Common.getUserName(), timeStamp, "getMultipleChoiceScores"));
        }
        return response;
    }
    @PostMapping("/add/{id_test}/tests")
    public BaseMessage addListQuestionsWithTest(@RequestBody List<Integer> idListQuestion, @PathVariable Integer id_test){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            String results=testService.addListQuestionsWithTest(idListQuestion,id_test);

            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, results);
            logger.info(Common.createMessageLog(idListQuestion, response, Common.getUserName(), timeStamp, "getMultipleChoiceScores"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(idListQuestion, response, Common.getUserName(), timeStamp, "getMultipleChoiceScores"));
        }
        return response;

    }


}