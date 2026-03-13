package com.flowtrack.controller.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowtrack.entity.database.TaskState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

  private UUID id;
  private String title;
  private String description;
  @JsonProperty("status_id")
  private Long statusId;
  private TaskState state;
  @JsonProperty("assigned_to")
  private UserDTO assignedTo;
  @JsonProperty("project_id")
  private UUID projectId;

  @Data
  @Builder
  public static class UserDTO {
    private UUID id;
    private String username;
    private String email;
  }
}
