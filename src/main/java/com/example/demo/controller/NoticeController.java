package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.NoticeOutput;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.service.ITaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification")
public class NoticeController {
    Logger logger = Logger.getLogger("NoticeController");
    @Autowired
    ITaskService taskService;
    //check thong bao
    @GetMapping("/test/{testid}")
    public BaseMessage getNoticeByUserId( @PathVariable Integer testid) {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        int userId = Common.getUserId();
        int testId = testid;
        try {
            NoticeOutput resultTask = taskService.getNotice(userId, testId);
            if(resultTask.isCheck()) {
                response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, resultTask);
                logger.info(Common.createMessageLog(userId + " " + testId, response, Common.getUserName(), timeStamp, "getNoticeByUserId"));
            }else{
                response = new ResponseEntityBO<>(Constants.ERROR_RESPONSE, "Chưa có điểm tự luận", timeStamp, resultTask);
                logger.error(Common.createMessageLog(userId + " " + testId, response, Common.getUserName(), timeStamp, "getNoticeByUserId"));
            }

        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, "Không xác định", timeStamp);
            logger.error(Common.createMessageLog(userId + " " + testId, response, Common.getUserName(), timeStamp, "getNoticeByUserId"));
        }
        return response;
    }
}
