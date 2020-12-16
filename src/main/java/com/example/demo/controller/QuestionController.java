package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.service.IQuestionService;
import com.example.demo.service.impl.QuestionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    IQuestionService questionService;
    Logger logger = Logger.getLogger("QuestionController");

    @GetMapping
    public BaseMessage findAll() {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        long start = Common.getTimeStamp();
        try {
            List<QuestionEntity> lst = questionService.findAll();
            if (Common.isNullOrEmpty(lst)) {
                //sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
                response = new BaseMessage(Constants.ERROR_RESPONSE, "Khong co cau hoi nao!", timeStamp);
                logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
//                response.setErrorCode(Constants.ERROR_RESPONSE);
//                response.setMessage("Khong co cau hoi nao!"); // lay tu cau hinh
//                response.setTimestamp(timeStamp);
            } else {
                //tham chiếu đến đối tượng cần trả về
                response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, lst);
                logger.info(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
//                response.setErrorCode(Constants.SUCCESS_RESPONSE);
//                response.setMessage("Thanh cong"); // lay tu cau hinh
//                response.setResult(lst);
//                response.setTimestamp(timeStamp);
            }
        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
            logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
//            response.setErrorCode(Constants.ERROR_RESPONSE);
//            response.setMessage(e.getMessage()); // lay tu cau hinh
//            response.setTimestamp(timeStamp);

        }
        return response;
    }

    @GetMapping("/{id}")
    public BaseMessage findById(@PathVariable Integer id) {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            Optional<QuestionEntity> questionEntity = questionService.findById(id);
            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, questionEntity.get());
            logger.info(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "findbyId"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy id câu hỏi", timeStamp);
            logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "findByid"));
        }
        return response;
    }

    @GetMapping("/findbyname/{question}")
    public BaseMessage findByQuestion(@PathVariable String question) {


        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            List<QuestionEntity> questionEntityList = questionService.findByQuestion(question);
            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, questionEntityList);
            logger.info(Common.createMessageLog(question, response, Common.getUserName(), timeStamp, "findByQuestion"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy câu hỏi nào!", timeStamp);
            logger.error(Common.createMessageLog(question, response, Common.getUserName(), timeStamp, "findByQuestion"));
        }
        return response;
    }

    @PostMapping("/save/{id}")
    public BaseMessage save(@RequestBody QuestionEntity question, @PathVariable Integer id) {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
//        long start = Common.getTimeStamp();
        try {
            Optional<QuestionEntity> questionEntity = questionService.findById(id);
            response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Sửa Thành công", timeStamp);
            questionService.save(question, id);
            logger.info(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "save"));
        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "không tìm thấy id câu hỏi", timeStamp);
            logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "save"));
        }

        return response;
    }

    @PostMapping("/saveall/{lv}")
    public BaseMessage saveAll(@RequestBody List<QuestionEntity> questions, @PathVariable Character lv) {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            if (Common.isNullOrEmpty(questions)) {
                response = new BaseMessage(Constants.ERROR_RESPONSE, "không thêm các câu hỏi được", timeStamp);
                logger.error(Common.createMessageLog(questions, response, Common.getUserName(), timeStamp, "saveall"));
            } else {
                questionService.saveAll(questions, lv);
                response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Thêm danh sách Thành công", timeStamp);
                logger.info(Common.createMessageLog(questions, response, Common.getUserName(), timeStamp, "saveall"));
                return response;
            }

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
            logger.error(Common.createMessageLog(questions, response, Common.getUserName(), timeStamp, "saveall"));
        }

        return response;
    }

    @DeleteMapping("/delete/{id}")
    public BaseMessage deleteById(@PathVariable Integer id) {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
//        long start = Common.getTimeStamp();
        try {
            Optional<QuestionEntity> questionEntity = questionService.findById(id);
            response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Xóa Thành công", timeStamp);
            questionService.deleteById(id);
            logger.info(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "deleteById"));
        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy id câu hỏi", timeStamp);
            logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "deleteById"));
        }

        return response;
    }

}
