package com.flowtrack.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowtrack.controller.request.ProjectRequest;
import com.flowtrack.controller.request.TaskStatusRequest;
import com.flowtrack.controller.request.TaskStatusSwapRequest;
import com.flowtrack.controller.response.ProjectResponse;
import com.flowtrack.controller.response.TaskStatusResponse;
import com.flowtrack.services.ProjectService;
import com.flowtrack.services.TaskStatusService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/v1/project")
public class ProjectController {

  private final ProjectService projectService;
  private final TaskStatusService taskStatusService;

  @GetMapping("/authorized/user")
  public ResponseEntity<List<ProjectResponse>> getProjectsByUser() {
    return projectService.getProjectsByUser();
  }

  @GetMapping("")
  public ResponseEntity<ProjectResponse> getProjectById(@RequestParam UUID projectId) {
    return projectService.getProjectById(projectId);
  }

  @PostMapping("")
  public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest request) {
    return projectService.createProject(request);
  }

  @PostMapping("/status")
  public ResponseEntity<TaskStatusResponse> createTaskStatus(@RequestBody TaskStatusRequest request) {
    return taskStatusService.createTaskStatus(request);
  }

  @GetMapping("/statuses")
  public ResponseEntity<List<TaskStatusResponse>> getStatusesByProject(@RequestParam("project_id") UUID projectId) {
    return taskStatusService.getTaskStatusByProjectId(projectId);
  }

  @PutMapping("/swap/status/position")
  public ResponseEntity<TaskStatusSwapRequest> swapTaskStatusPosition(@RequestBody TaskStatusSwapRequest request) {
    return taskStatusService.swapTaskStatusPosition(request);
  } 
}
