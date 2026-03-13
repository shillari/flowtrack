package com.flowtrack.controller.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskStatusSwapResponse {

  @JsonProperty("id_1")
  private Long id1;
  @JsonProperty("id_2")
  private Long id2;
  @JsonProperty("position_1")
  private BigDecimal position1;
  @JsonProperty("position_2")
  private BigDecimal position2;
}
