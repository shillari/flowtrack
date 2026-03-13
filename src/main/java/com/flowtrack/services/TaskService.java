package com.flowtrack.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.flowtrack.controller.request.TaskRequest;

public interface TaskService {

  ResponseEntity<TaskRequest> getTaskById(UUID id);

  ResponseEntity<TaskRequest> createTask(TaskRequest request);

  ResponseEntity<List<TaskRequest>> getAllTasksByProject(UUID projectId);

  ResponseEntity<List<TaskRequest>> getAllTasksByCurrentUser();

}
