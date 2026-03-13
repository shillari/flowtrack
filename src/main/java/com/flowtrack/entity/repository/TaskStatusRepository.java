package com.flowtrack.entity.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.flowtrack.entity.database.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

  @Query("""
      SELECT t 
      FROM TaskStatus t 
      WHERE t.project.id= :projectId
      ORDER BY t.position ASC
      """)
  List<TaskStatus> findAllByProjectIdOrderByPositionAsc(UUID projectId);

  @Modifying
  @Query("""
    UPDATE TaskStatus SET position = :position WHERE id = :id
  """)
  void setNewPosition(Long id, BigDecimal position);

  @Query("""
      SELECT t 
      FROM TaskStatus t 
      WHERE t.project.id= :projectId
      AND t.id= :id
      """)
  Optional<TaskStatus> findByIdProjectId(UUID id, UUID projectId);
}
