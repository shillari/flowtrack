package com.flowtrack.services.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowtrack.controller.request.ProjectRequest;
import com.flowtrack.controller.response.ProjectResponse;
import com.flowtrack.entity.database.Project;
import com.flowtrack.entity.database.User;
import com.flowtrack.entity.mapper.ProjectMapper;
import com.flowtrack.entity.repository.ProjectRepository;
import com.flowtrack.entity.repository.UserRepository;
import com.flowtrack.services.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public ResponseEntity<ProjectResponse> createProject(ProjectRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String user = authentication.getName();

    Optional<User> usr = userRepository.findByEmail(user);
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    Project newProject = ProjectMapper.toDB(request);
    newProject.setOwner(usr.get());
    newProject = projectRepository.save(newProject);

    return ResponseEntity.ok(ProjectMapper.toJson(newProject));
  }

  @Override
  public ResponseEntity<List<ProjectResponse>> getProjectsByUser() {
    // Verify the authenticated user and extract their credential (email).
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String user = authentication.getName();
    
    Optional<User> usr = userRepository.findByEmail(user);
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    Optional<List<Project>> projects = projectRepository.findByOwner(usr.get());
    if (projects == null || !projects.isPresent()) {
      return ResponseEntity.ok(Arrays.asList());
    }

    return ResponseEntity.ok(ProjectMapper.projectsToJson(projects.get()));
  }

  @Override
  public ResponseEntity<ProjectResponse> getProjectById(UUID id) {
    if (id == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    // Verify the authenticated user and extract their credential (email).
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String user = authentication.getName();

    Optional<User> usr = userRepository.findByEmail(user);
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    Optional<Project> proj = projectRepository.findByIdAndOwner(id, usr.get().getId());
    if (proj == null || !proj.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(ProjectMapper.toJson(proj.get()));
  }

}
