package com.flowtrack.controller.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.flowtrack.entity.database.TaskState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {

  private UUID id;
  private String title;
  private String description;
  private OwnerDTO owner;
  private TaskState state;
  private Instant createdAt;
  private Instant endedAt;
  private Set<TaskStatusResponse> statuses;

  @Data
  @Builder
  public static class OwnerDTO {
    private UUID id;
    private String username;
    private Boolean activ;
  }
}
