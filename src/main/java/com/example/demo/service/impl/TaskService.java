package com.example.demo.service.impl;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.QuestionTestEntity;
import com.example.demo.entity.TaskEntity;
import com.example.demo.payload.request.ResultEssayRequest;
import com.example.demo.repository.QuestionTestRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
public class TaskService implements ITaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    QuestionTestRepository questionTestRepository;
    List<QuestionEntity> listEssay=new ArrayList<>();


    @Override
    public String multipleChoiceResultTask(List<QuestionEntity> questionEntityList) {
        int size = questionEntityList.size();
        int trueQuestion = 0;
        long timeStamp = Common.getTimeStamp();
        List<QuestionTestEntity> questionTestEntityList = questionTestRepository.findAll();
        for (int i = 0; i < questionTestEntityList.size(); i++) {
            if (questionTestEntityList.get(i).getQuestion().getQuestionType().equals(Constants.TN)) {
                for (int j = i; j < questionTestEntityList.size(); j++) {
                    if (questionEntityList.get(i).getId() == questionTestEntityList.get(j).getQuestion().getId()) {
                        if (questionEntityList.get(i).getAnswer().equals(questionTestEntityList.get(j).getQuestion().getAnswer())) {
                            trueQuestion++;
                            break;
                        }
                    }
                }
            }else {
                size--;
                //them nhung cau hoi tu luan vao list toan cuc
                listEssay.add(questionEntityList.get(i));
            }
        }

        String finalResult=trueQuestion + "/" + size;



//        TaskEntity taskEntity=new TaskEntity();
//        taskEntity.setTaskAwnser(finalResult);
//        taskEntity.setQuestionTest();


        return finalResult;
    }

    @Override
    public String essayTask(List<ResultEssayRequest> essayRequest) {
        ListIterator litr = listEssay.listIterator();
        while (litr.hasNext()) {
            Object element = litr.next();
            litr.set(element + " (PASS)");
        }
        return null;
    }
}
