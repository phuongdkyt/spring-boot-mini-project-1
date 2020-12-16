package com.example.demo.service.impl;

import com.example.demo.common.Constants;
import com.example.demo.entity.QuestionTestEntity;
import com.example.demo.entity.TaskEntity;
import com.example.demo.entity.TestEntity;
import com.example.demo.repository.QuestionTestRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TestRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.IResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResuiltService implements IResultService {
    @Autowired
    TestRepository testRepository;
    UserPrincipal userPrincipal;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private QuestionTestRepository questionTestRepository;

    public String multipleChoiceAnswers(String testName) {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TaskEntity> taskEntityList = taskRepository.findAllByUser_Id(3);
        TestEntity testEntity = testRepository.findByTestName(testName);
        List<QuestionTestEntity> questionTestEntityList = questionTestRepository.findAllByTest_Id(testEntity.getId());

        int size = questionTestEntityList.size();//
        int trueQuestion = 0;


        for (int i = 0; i < questionTestEntityList.size(); i++) {
//            System.out.println("-"+taskEntityList.get(i).getQuestionTest().getQuestion().getId());
           System.out.println(questionTestEntityList.get(i).getQuestion().getQuestionType());
            if (questionTestEntityList.get(i).getQuestion().getQuestionType().equals(Constants.TN)) {
                for (int j = 0; j < questionTestEntityList.size(); j++) {

                    if (taskEntityList.get(i).getQuestionTest().getQuestion().getId() == questionTestEntityList.get(j).getQuestion().getId()) {
                        System.out.println("+"+taskEntityList.get(i).getQuestionTest().getQuestion().getId());
                        System.out.println(taskEntityList.get(i).getTaskAwnser());
                        System.out.println("-"+questionTestEntityList.get(j).getQuestion().getId()+"-");
                        System.out.println("-"+questionTestEntityList.get(j).getQuestion().getAnswer());
                        if (taskEntityList.get(i).getTaskAwnser().equals(questionTestEntityList.get(j).getQuestion().getAnswer())) {
                            trueQuestion++;
                            break;
                        }
                    }
                }
            } else {
                size--;
            }
        }

        return trueQuestion + "/" + size;
    }
}
