package com.taskmanager.services;

import com.taskmanager.dao.model.TaskModel;
import com.taskmanager.dto.ResponseDTO.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author harjeevanSingh
 */


@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskModel taskModel;

    public ResponseDTO<?> addTask() {
        log.info("Add Task");
        return new ResponseDTO<>("Add Task");
    }

    public ResponseDTO<?> getTask(Long id) {
        log.info("Get Task details: " + id);
        return new ResponseDTO<>("Task details");
    }

    public ResponseDTO<?> getAllTasks() {
        log.info("All tasks");
        return new ResponseDTO<>(taskModel.getAllTasks());
    }
}
