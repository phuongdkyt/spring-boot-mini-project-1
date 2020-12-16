package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.service.IResultService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {
    @Autowired
    IResultService resultService;
    Logger logger = Logger.getLogger("QuestionController");
    @GetMapping("/{testName}")
    public BaseMessage sendAnswer(@RequestBody List<QuestionEntity> questionEntityList,@PathVariable String testName){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            String resultTask = resultService.multipleChoiceAnswers(testName);
            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, resultTask);
            logger.info(Common.createMessageLog(questionEntityList, response, Common.getUserName(), timeStamp, "findTaskResutl"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(questionEntityList, response, Common.getUserName(), timeStamp, "findTaskResutl"));
        }
        return response;
    }
}
