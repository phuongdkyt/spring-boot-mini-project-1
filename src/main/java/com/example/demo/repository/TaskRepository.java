package com.example.demo.repository;

import com.example.demo.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity,Integer> {
    List<TaskEntity> findAllByUser_Id( Integer id);


}
