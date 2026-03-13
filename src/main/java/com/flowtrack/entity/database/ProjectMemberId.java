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
public class ProjectMemberId implements Serializable {

  private UUID projectId;
  private UUID userId;
}
