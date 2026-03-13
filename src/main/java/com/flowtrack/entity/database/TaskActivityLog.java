package com.flowtrack.entity.database;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="task_activity_log")
public class TaskActivityLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @Column(name="action")
  private String action;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="performed_by")
  private User performedBy;

  @Column(name="created_at")
  @CreationTimestamp
  private Instant createdAt;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="task_id")
  private Task task;

}
