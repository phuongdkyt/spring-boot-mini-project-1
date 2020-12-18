package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.NoticeOutput;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.QuestionTestEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.payload.request.EssayScoringRequest;
import com.example.demo.repository.QuestionTestRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.ITaskService;
import com.example.demo.service.impl.TaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    ITaskService taskService;
    @Autowired
    TaskRepository taskRepository;
    Logger logger = Logger.getLogger("QuestionController");
    @PostMapping("/{testName}")
    public BaseMessage sendAnswer(@RequestBody  List<QuestionEntity> questionEntityList,@PathVariable String testName){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            String resultTask = taskService.sendAnswer(questionEntityList,testName);
            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, resultTask);
            logger.info(Common.createMessageLog(questionEntityList, response, Common.getUserName(), timeStamp, "findTaskResutl"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(questionEntityList, response, Common.getUserName(), timeStamp, "findTaskResutl"));
        }
        return response;
    }

    @GetMapping("/getnotice/user/{userid}/test/{testid}")
    public BaseMessage getNoticeByUserId(@PathVariable Integer userid,@PathVariable Integer testid){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        int userId = userid;
        int testId = testid;
        try {
            NoticeOutput resultTask = taskService.getNotice(userId,testId);
            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, resultTask);
            logger.info(Common.createMessageLog(userId + " " + testId, response, Common.getUserName(), timeStamp, "findTaskResutl"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(userId + " " + testId, response, Common.getUserName(), timeStamp, "findTaskResutl"));
        }
        return response;
    }

    @GetMapping("/multiplechoiceresults/{testName}")
    public BaseMessage getMarkOnTotalQuestion(@PathVariable String testName){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            Object object= taskService.getMarkOnTotalQuestion(testName);
            System.out.println(object.toString());
            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, object);
            logger.info(Common.createMessageLog(testName, response, Common.getUserName(), timeStamp, "getMarkOnTotalQuestion"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(testName, response, Common.getUserName(), timeStamp, "getMarkOnTotalQuestion"));
        }
        return response;
    }
    @GetMapping("/essayscoreresults/{testName}")
    public BaseMessage getEssayScoreResults(@PathVariable String testName){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            String results= taskService.getEssayScoreResults(testName);

            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, results);
            logger.info(Common.createMessageLog(testName, response, Common.getUserName(), timeStamp, "getEssayScoreResults"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(testName, response, Common.getUserName(), timeStamp, "getEssayScoreResults"));
        }
        return response;
    }
    @PutMapping("/essayscoreresults")
    public BaseMessage essayScoring(@RequestBody List<EssayScoringRequest> essayScoringRequest){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            String results=taskService.essayScoring(essayScoringRequest);

            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, results);
            logger.info(Common.createMessageLog(essayScoringRequest, response, Common.getUserName(), timeStamp, "getEssayScoreResults"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(essayScoringRequest, response, Common.getUserName(), timeStamp, "getEssayScoreResults"));
        }
        return response;
    }
    @GetMapping("/result/user/{id}/test/{testName}")
    public BaseMessage getMultipleChoiceScores(@PathVariable Integer id,@PathVariable String testName){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            String results=taskService.getMultipleChoiceScores(id,testName);

            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, results);
            logger.info(Common.createMessageLog(testName, response, Common.getUserName(), timeStamp, "getEssayScoreResults"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(testName, response, Common.getUserName(), timeStamp, "getEssayScoreResults"));
        }
        return response;
    }

}
