package com.flowtrack.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.flowtrack.controller.request.ProjectRequest;
import com.flowtrack.controller.response.ProjectResponse;

public interface ProjectService {

  ResponseEntity<ProjectResponse> createProject(ProjectRequest request);

  ResponseEntity<List<ProjectResponse>> getProjectsByUser();

  ResponseEntity<ProjectResponse> getProjectById(UUID id);

}
