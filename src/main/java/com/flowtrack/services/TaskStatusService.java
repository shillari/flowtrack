package com.flowtrack.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.flowtrack.controller.request.TaskStatusRequest;
import com.flowtrack.controller.request.TaskStatusSwapRequest;
import com.flowtrack.controller.response.TaskStatusResponse;

public interface TaskStatusService {

  ResponseEntity<TaskStatusResponse> createTaskStatus(TaskStatusRequest request);

  ResponseEntity<List<TaskStatusResponse>> getTaskStatusByProjectId(UUID projectId);

  ResponseEntity<TaskStatusSwapRequest> swapTaskStatusPosition(TaskStatusSwapRequest request);

}
