package com.example.demo.service.impl;

import com.example.demo.common.Constants;
import com.example.demo.entity.*;
import com.example.demo.payload.request.EssayScoringRequest;
import com.example.demo.repository.QuestionTestRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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
            taskEntity.setTest(testEntity);
            taskRepository.save(taskEntity);
        }
        return "ok";
    }

    @Override
    public NoticeOutput getNotice(Integer userId, Integer testId) {
        NoticeOutput reponse = new NoticeOutput();
        //B1: lay so cau hoi

        TestEntity testEntity = new TestEntity();
        testEntity.setId(testId);
        Long numOfQuestion = questionTestRepository.countAllByTest(testEntity);
        //B2:lay bai da cham
        Long questionMarked = taskRepository.countResultMarked(userId, testId);
        //B3: so sanh
//        Long questioMarked = 4L;
        if (numOfQuestion == questionMarked){
            reponse.setCheck(true);
        }

        return reponse;
    }
    public Long getMarkOnTotalQuestion(String testName){
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id =userPrincipal.getId();
        TestEntity testEntity= testRepository.findByTestName(testName);
       Long result=  taskRepository.getMarkOnTotalQuestion(id,"TN",testEntity.getId());
      return  result;
    }
    public String essayScoring(List<EssayScoringRequest> essayScoringRequestList){
        TestEntity testEntity =testRepository.findByTestName("bai1");
        List<QuestionTestEntity> questionTestEntityLíst=questionTestRepository.findAllByTest_Id(testEntity.getId());
        for(int i=0;i<questionTestEntityLíst.size();i++){

                for (int j = 0; j < essayScoringRequestList.size(); j++) {
                    if (questionTestEntityLíst.get(i).getQuestion().getId() == essayScoringRequestList.get(j).getId()) {
                        TaskEntity taskEntity = taskRepository.findByQuestionTestId(questionTestEntityLíst.get(i).getId());
                        taskEntity.setMark(essayScoringRequestList.get(j).getMark());
                        taskRepository.save(taskEntity);
                        break;

                    }
            }
        }
        return "ok";
    }

    @Override
    public String getMultipleChoiceScores() {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TaskEntity> taskEntityList = taskRepository.findAllByUser_Id(1);
        TestEntity testEntity = testRepository.findByTestName("bai1");
        List<QuestionTestEntity> questionTestEntityList = questionTestRepository.findAllByTest_Id(testEntity.getId());

        int size = questionTestEntityList.size();

        for (int i = 0; i < questionTestEntityList.size(); i++) {
            if (questionTestEntityList.get(i).getQuestion().getQuestionType().equals(Constants.TN)) {
                for (int j = 0; j < questionTestEntityList.size(); j++) {

                    if (taskEntityList.get(i).getQuestionTest().getQuestion().getId() == questionTestEntityList.get(j).getQuestion().getId()) {
                        if (taskEntityList.get(i).getTaskAwnser().equals(questionTestEntityList.get(j).getQuestion().getAnswer())) {
                            TaskEntity taskEntity = taskRepository.findByQuestionTestId(questionTestEntityList.get(i).getId());
                            taskEntity.setMark(1.0);
                            taskRepository.save(taskEntity);
                            break;
                        } else {
                            TaskEntity taskEntity = taskRepository.findByQuestionTestId(questionTestEntityList.get(i).getId());
                            taskEntity.setMark(0.0);
                            taskRepository.save(taskEntity);
                            break;
                        }
                    }
                }
                }

        }
        return "ok";
    }

    @Override
    public Long getEssayScoreResults(String testName) {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id =userPrincipal.getId();
        TestEntity testEntity= testRepository.findByTestName(testName);
//        List<QuestionTestEntity> questionTestEntityList=questionTestRepository.
        Long scoreResults=  taskRepository.getEssayScoreResults(id,"TL",testEntity.getId());
        return  scoreResults;
    }

}
