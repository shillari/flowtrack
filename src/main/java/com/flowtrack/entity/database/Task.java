package com.flowtrack.entity.database;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="task")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @UuidGenerator
  @Column(name="id")
  private UUID id;

  @Column(name="title", nullable=false, unique=true)
  private String title;

  @Column(name="description")
  private String description;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="status_id")
  private TaskStatus status;

  @Enumerated(EnumType.STRING)
  private TaskState state = TaskState.OPEN;

  @OneToMany(fetch=FetchType.LAZY, mappedBy="task", orphanRemoval = true)
  private Set<TaskActivityLog> logs = new HashSet<>();

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="assigned_to")
  private User assignedTo;

  @OneToMany(mappedBy="task")
  private Set<TaskLabel> labels = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;
}
