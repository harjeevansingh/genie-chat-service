package com.taskmanager.controllers.v1;

import com.taskmanager.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author harjeevanSingh
 */

@RestController
@RequestMapping("/api/v1/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<?> addTask() {
        log.info("Request received to add Task");
        return ResponseEntity.ok(taskService.addTask());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> addTask(@PathVariable Long id) {
        log.info("Request received for Task details: " + id);
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getTasks() {
        log.info("Request received for all tasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }
}
