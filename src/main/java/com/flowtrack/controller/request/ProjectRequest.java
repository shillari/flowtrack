package com.flowtrack.controller.request;

import java.time.Instant;
import java.util.UUID;

import com.flowtrack.entity.database.TaskState;
import com.flowtrack.entity.database.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

  private UUID id;
  private String title;
  private String description;
  private User owner;
  private TaskState state;
  private Instant endedAt;
  
}
