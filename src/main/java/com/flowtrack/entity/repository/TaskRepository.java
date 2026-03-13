package com.flowtrack.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.flowtrack.entity.database.Task;
import com.flowtrack.entity.database.User;

public interface TaskRepository extends JpaRepository<Task, Long>{

  Optional<Task> findById(UUID id);

  @Query("""
    SELECT COUNT(ts)
    FROM TaskStatus ts
    JOIN ProjectMember pm ON pm.project.id = ts.project.id
    WHERE ts.id = :statusId
    AND ts.project.id = :projectId
    AND pm.user.id = :userId
    """)
  long validateTaskCreation(UUID projectId, Long statusId, UUID userId);

  @Modifying
  @Query(value = """
  INSERT INTO task (title, description, state, assigned_to, project_id, status_id)
  SELECT :title, :description, :state, :userId, :projectId, :statusId
  WHERE EXISTS (
      SELECT 1 
      FROM project_member pm 
      WHERE pm.project_id = :projectId 
      AND pm.user_id = :userId
  )
  AND EXISTS (
      SELECT 1
      FROM task_status ts
      WHERE ts.id = :statusId
      AND ts.project_id = :projectId
  )
  """, nativeQuery = true)
  void saveTaskByProjectAndStatus(String title, String description, String state, User user, UUID projectId, Long statusId);

  Optional<List<Task>> findByProjectId(UUID projectId);

  Optional<List<Task>> findByAssignedTo(User user);
}
