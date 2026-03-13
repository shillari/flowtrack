package com.flowtrack.entity.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowtrack.entity.database.Project;
import com.flowtrack.entity.database.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  Optional<List<Project>> findByOwner(User user);

  Optional<Project> findById(UUID id);

  @Query("""
    SELECT p FROM Project p
    WHERE p.id = :projectId
    AND (
        p.owner.id = :userId
        OR EXISTS (
            SELECT pm FROM ProjectMember pm
            WHERE pm.project.id = p.id
            AND pm.user.id = :userId
        )
    )
  """)
  Optional<Project> findByIdAndOwner(UUID projectId, UUID userId);

  @Query("""
      SELECT MAX(position) from TaskStatus
      """)
  BigDecimal getMaxPosition();
  
}
