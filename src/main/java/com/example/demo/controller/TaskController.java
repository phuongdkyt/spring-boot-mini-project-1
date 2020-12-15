package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.QuestionTestEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
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
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    ITaskService taskService;
    Logger logger = Logger.getLogger("QuestionController");
    @PostMapping("/result")
    public BaseMessage findTaskResutl(@RequestBody  List<QuestionEntity> questionEntityList){
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            String resultTask = taskService.multipleChoiceResultTask(questionEntityList);
            response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, resultTask);
            logger.info(Common.createMessageLog(questionEntityList, response, Common.getUserName(), timeStamp, "findTaskResutl"));

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(questionEntityList, response, Common.getUserName(), timeStamp, "findTaskResutl"));
        }
        return response;
    }
}
