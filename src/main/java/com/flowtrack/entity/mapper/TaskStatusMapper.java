package com.flowtrack.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.flowtrack.controller.request.TaskStatusRequest;
import com.flowtrack.controller.response.TaskStatusResponse;
import com.flowtrack.entity.database.Project;
import com.flowtrack.entity.database.TaskStatus;

public class TaskStatusMapper {

  public static TaskStatus toDb(TaskStatusRequest status, Project project) {

    TaskStatus newStatus = new TaskStatus();

    newStatus.setName(status.getName());
    newStatus.setColor(status.getColor());
    newStatus.setPosition(status.getPosition());
    newStatus.setProject(project);

    return newStatus;
  }

  public static TaskStatusResponse toJson(TaskStatus status) {
    return TaskStatusResponse.builder()
            .name(status.getName())
            .color(status.getColor())
            .position(status.getPosition())
            .id(status.getId())
            .projectId(status.getProject().getId())
            .build();
  }

  public static List<TaskStatusResponse> listToJson(List<TaskStatus> statuses) {
    if (statuses == null || statuses.isEmpty()) return new ArrayList<>();

    List<TaskStatusResponse> response = new ArrayList<>();

    for (TaskStatus status : statuses) {
      response.add(TaskStatusResponse.builder()
                  .id(status.getId())
                  .name(status.getName())
                  .color(status.getColor())
                  .position(status.getPosition())
                  .projectId(status.getProject().getId())
                  .build());
    }

    return response;
  }
}
