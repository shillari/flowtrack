package com.flowtrack.entity.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.flowtrack.controller.request.ProjectRequest;
import com.flowtrack.controller.response.ProjectResponse;
import com.flowtrack.controller.response.TaskStatusResponse;
import com.flowtrack.entity.database.Project;
import com.flowtrack.entity.database.TaskStatus;

public class ProjectMapper {
  
  public static Project toDB(ProjectRequest request) {

    Project proj = new Project();

    proj.setId(request.getId());
    proj.setTitle(request.getTitle());
    proj.setDescription(request.getDescription());
    proj.setOwner(request.getOwner());

    return proj;
  }

  public static ProjectResponse toJson(Project project) {
    ProjectResponse response = new ProjectResponse();

    response.setId(project.getId());
    response.setTitle(project.getTitle());
    response.setDescription(project.getDescription());
    response.setCreatedAt(project.getCreatedAt());
    response.setOwner(ProjectResponse.OwnerDTO
        .builder()
        .id(project.getOwner().getId())
        .username(project.getOwner().getUsername())
        .activ(project.getOwner().getActiv())
        .build());
    response.setState(project.getState());
    Set<TaskStatusResponse> taskStatuses = new HashSet<>();
    for (TaskStatus st : project.getStatuses()) {
      taskStatuses.add(TaskStatusResponse.builder()
                        .id(st.getId())
                        .name(st.getName())
                        .color(st.getColor())
                        .position(st.getPosition())
                        .projectId(st.getProject().getId())
                        .build());
    }
    response.setStatuses(taskStatuses);
    response.setEndedAt(project.getEndedAt());

    return response;
  }

  public static List<ProjectResponse> projectsToJson(List<Project> projects) {
    if (projects == null || projects.isEmpty()) {
      return new ArrayList<>();
    }

    List<ProjectResponse> response = new ArrayList<>();
    
    for (Project proj : projects) {
      ProjectResponse.OwnerDTO ownerDTO = ProjectResponse.OwnerDTO
        .builder()
        .id(proj.getOwner().getId())
        .username(proj.getOwner().getUsername())
        .activ(proj.getOwner().getActiv())
        .build();
      Set<TaskStatusResponse> taskStatuses = new HashSet<>();
      for (TaskStatus st : proj.getStatuses()) {
        taskStatuses.add(TaskStatusResponse.builder()
                          .id(st.getId())
                          .name(st.getName())
                          .color(st.getColor())
                          .position(st.getPosition())
                          .projectId(st.getProject().getId())
                          .build());
      }
      response.add(ProjectResponse.builder()
                    .id(proj.getId())
                    .title(proj.getTitle())
                    .description(proj.getDescription())
                    .createdAt(proj.getCreatedAt())
                    .endedAt(proj.getEndedAt())
                    .owner(ownerDTO)
                    .state(proj.getState())
                    .statuses(taskStatuses)
                    .build());
    }

    return response;
  }
}
