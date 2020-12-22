package com.example.demo.service.impl;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.*;
import com.example.demo.payload.request.EssayScoringRequest;
import com.example.demo.repository.*;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Autowired
    UserTestRepository userTestRepository;

    UserPrincipal userPrincipal;

    @Override
    public String sendAnswer(List<QuestionEntity> questionEntityList, Integer test_id) {
//		userPrincipal =
//				(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Tìm kiếm người dùng theo id
        Optional<UserEntity> userEntity = userRepository.findById(Common.getUserId());
//		System.out.println(userPrincipal.getId());
        System.out.println(userEntity.get().getId());
        //tim kiem ten bai thi
        Optional<TestEntity> testEntity = testRepository.findById(test_id);
        //tim kiem theo id bai thi
        List<QuestionTestEntity> questionTestEntityList = questionTestRepository.findAllByTest_Id(test_id);

        for (int i = 0; i < questionTestEntityList.size(); i++) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setAnswer(questionEntityList.get(i).getAnswer());
            taskEntity.setQuestionTest(questionTestEntityList.get(i));
            taskEntity.setUser(userEntity.get());
            taskEntity.setTest(testEntity.get());
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
        if (numOfQuestion == questionMarked && questionMarked != 0) {
            reponse.setCheck(true);
        }

        return reponse;
    }

    public String getMarkOnTotalQuestion(Integer test_id) {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userPrincipal.getId();

        Long result = taskRepository.getMarkOnTotalQuestion(id, Constants.TN, test_id);
        Long totalResult = taskRepository.getTotalQuestion(id, Constants.TN, test_id);
        return result + "/" + totalResult;
    }

    //cham diem tu luan
    public String essayScoring(List<EssayScoringRequest> essayScoringRequestList, Integer test_id, Integer userId) {
//        Optional<UserEntity> userEntity=userRepository.findById(1);

        //tim kiem theo bai test so 1
        List<QuestionTestEntity> questionTestEntityList = questionTestRepository.findAllByTest_Id(test_id);
        for (int i = 0; i < questionTestEntityList.size(); i++) {
            if (questionTestEntityList.get(i).getQuestion().getQuestionType().equals(Constants.TL)) {
                for (int j = 0; j < essayScoringRequestList.size(); j++) {
                    if (questionTestEntityList.get(i).getQuestion().getId() == essayScoringRequestList.get(j).getId()) {
                        TaskEntity taskEntity = taskRepository.findByQuestionTestIdAndUserId(questionTestEntityList.get(i).getId(), userId);
                        taskEntity.setMark(essayScoringRequestList.get(j).getMark());
                        taskRepository.save(taskEntity);

                    }
                }
            }
        }
        return "ok";
    }

    //lay diem trac nhiem
    @Override
    public String getMultipleChoiceScores(Integer test_id) {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            List<TaskEntity> taskEntityList = taskRepository.findAllByTest_IdAndUser_Id(test_id, Common.getUserId());
            List<QuestionTestEntity> questionTestEntityList = questionTestRepository.findAllByTest_Id(test_id);

            int size = questionTestEntityList.size();

            for (int i = 0; i < questionTestEntityList.size(); i++) {
                if (questionTestEntityList.get(i).getQuestion().getQuestionType().equals(Constants.TN)) {
                    for (int j = 0; j < questionTestEntityList.size(); j++) {

                        if (taskEntityList.get(i).getQuestionTest().getQuestion().getId() == questionTestEntityList.get(j).getQuestion().getId()) {
                            if (taskEntityList.get(i).getAnswer().equals(questionTestEntityList.get(j).getQuestion().getAnswer())) {
                                TaskEntity taskEntity = taskRepository.findByQuestionTestIdAndUserId(questionTestEntityList.get(i).getId(), Common.getUserId());
                                taskEntity.setMark(1.0);
                                taskRepository.save(taskEntity);
                                break;
                            }
                            if (!taskEntityList.get(i).getAnswer().equals(questionTestEntityList.get(j).getQuestion().getAnswer())) {
                                TaskEntity taskEntity = taskRepository.findByQuestionTestIdAndUserId(questionTestEntityList.get(i).getId(), Common.getUserId());
                                taskEntity.setMark(0.0);
                                taskRepository.save(taskEntity);
                                break;
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {

        }
        Long result = taskRepository.getMarkOnTotalQuestion(Common.getUserId(), Constants.TN, test_id);
        Long totalResult = taskRepository.getTotalQuestion(Common.getUserId(), Constants.TN, test_id);
        if (Common.isNullOrEmpty(result)) return null;
        return result + "/" + totalResult;
    }

    //lay diem tu luan
    @Override
    public String getEssayScoreResults(Integer test_id) {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userPrincipal.getId();

//        List<QuestionTestEntity> questionTestEntityList=questionTestRepository.
        Long scoreResults = taskRepository.getEssayScoreResults(id, Constants.TL, test_id);
        Long totalResult = taskRepository.getTotalQuestion(id, Constants.TL, test_id);
        if (Common.isNullOrEmpty(scoreResults)) return null;
        return scoreResults + "/" + totalResult * 10;
    }

}
