package com.flowtrack.controller.response;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskStatusResponse {

  private Long id;
  private String name;
  private String color;
  @JsonProperty("project_id")
  private UUID projectId;
  private BigDecimal position;
}
