package com.flowtrack.entity.database;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="project_member")
public class ProjectMember {

  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "projectId", column = @Column(name = "project_id")),
    @AttributeOverride(name = "userId", column = @Column(name = "user_id"))
  })
  private ProjectMemberId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("projectId")
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  private User user;

  @Column(name="joined_at")
  @CreationTimestamp
  private Instant joinedAt;

  @Enumerated(EnumType.STRING)
  private ProjectMemberRole role;
}
