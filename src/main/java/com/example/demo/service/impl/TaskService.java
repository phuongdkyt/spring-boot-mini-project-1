package com.example.demo.service.impl;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.*;
import com.example.demo.payload.request.ResultEssayRequest;
import com.example.demo.repository.QuestionTestRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    QuestionTestRepository questionTestRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TestRepository testRepository;
    UserPrincipal userPrincipal;

    @Override
    public String sendAnswer(List<QuestionEntity> questionEntityList,String testName) {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Tìm kiếm người dùng theo id
        Optional<UserEntity> userEntity = userRepository.findById(userPrincipal.getId());
        System.out.println(userPrincipal.getId());
        System.out.println(userEntity.get().getId());
        //tim kiem ten bai thi
        TestEntity testEntity=testRepository.findByTestName(testName);
        //tim kiem theo id bai thi
        List<QuestionTestEntity> questionTestEntityList = questionTestRepository.findAllByTest_Id(testEntity.getId());

        for (int i = 0; i < questionTestEntityList.size(); i++) {
            TaskEntity taskEntity=new TaskEntity();
            taskEntity.setTaskAwnser(questionEntityList.get(i).getAnswer());
            taskEntity.setQuestionTest(questionTestEntityList.get(i));
            taskEntity.setUser(userEntity.get());

            taskRepository.save(taskEntity);
        }
        return "ok";
    }


}
