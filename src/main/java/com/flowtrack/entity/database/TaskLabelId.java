package com.flowtrack.entity.database;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class TaskLabelId implements Serializable {

  private UUID labelId;
  private UUID taskId;
}
