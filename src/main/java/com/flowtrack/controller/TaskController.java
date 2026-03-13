package com.flowtrack.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowtrack.controller.request.TaskRequest;
import com.flowtrack.services.TaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/v1/task")
public class TaskController {

  private final TaskService taskService;

  @GetMapping("")
  public ResponseEntity<TaskRequest> getTaskById(@RequestParam UUID id) {
    return taskService.getTaskById(id);
  }
  
  @PostMapping("")
  public ResponseEntity<TaskRequest> createTask(TaskRequest request) {
    return taskService.createTask(request);
  }

  @GetMapping("/project")
  public ResponseEntity<List<TaskRequest>> getAllTasksByProject(@RequestParam("project_id") UUID projectId) {
    return taskService.getAllTasksByProject(projectId);
  }

  @GetMapping("/current/user")
  public ResponseEntity<List<TaskRequest>> getAllTasksByCurrentUser() {
    return taskService.getAllTasksByCurrentUser();
  }

}
