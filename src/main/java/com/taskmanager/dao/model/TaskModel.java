package com.taskmanager.dao.model;

import com.taskmanager.dao.master.TaskDAO;
import com.taskmanager.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author harjeevanSingh
 */

@Component
public class TaskModel {

    @Autowired
    private TaskDAO taskDAO;

    public List<Task> getAllTasks() {
        return taskDAO.findAllByOrderByIdDesc();
    }
}
