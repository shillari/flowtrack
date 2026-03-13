package com.flowtrack.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.flowtrack.controller.request.TaskRequest;
import com.flowtrack.entity.database.Task;
import com.flowtrack.entity.database.TaskState;
import com.flowtrack.entity.database.TaskStatus;
import com.flowtrack.entity.database.User;


public class TaskMapper {

  public static TaskRequest toJson(Task task) {
    User user = task.getAssignedTo();
    TaskRequest.UserDTO userDTO = TaskRequest.UserDTO.builder()
                                   .id(user.getId())
                                   .email(user.getEmail())
                                   .username(user.getUsername())
                                   .build();
    return TaskRequest.builder()
            .id(task.getId())
            .title(task.getTitle())
            .description(task.getDescription())
            .state(task.getState())
            .statusId(task.getStatus().getId())
            .assignedTo(userDTO)
            .build();
  }

  public static Task toDb(TaskRequest request, User user, Long statusId) {
    Task task = new Task();

    task.setTitle(request.getTitle());
    task.setDescription(request.getDescription());
    task.setState(TaskState.OPEN);
    task.setAssignedTo(user);
    TaskStatus st = new TaskStatus();
    st.setId(statusId);
    task.setStatus(st);

    return task;
  }

  public static List<TaskRequest> taskToJson(List<Task> tasks) {
    List<TaskRequest> response = new ArrayList<>();

    for (Task ts : tasks) {
      response.add(toJson(ts));
    }
    return response;
  }
}
