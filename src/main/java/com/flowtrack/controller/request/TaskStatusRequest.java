package com.flowtrack.controller.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusRequest {

  private String name;
  private String color;
  @JsonProperty("project_id")
  private UUID projectId;
  private BigDecimal position;
}
