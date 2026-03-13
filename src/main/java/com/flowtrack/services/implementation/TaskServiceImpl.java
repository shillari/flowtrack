package com.flowtrack.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.flowtrack.controller.request.TaskRequest;
import com.flowtrack.entity.database.Project;
import com.flowtrack.entity.database.Task;
import com.flowtrack.entity.database.User;
import com.flowtrack.entity.mapper.TaskMapper;
import com.flowtrack.entity.repository.ProjectRepository;
import com.flowtrack.entity.repository.TaskRepository;
import com.flowtrack.entity.repository.UserRepository;
import com.flowtrack.services.TaskService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  @Override
  public ResponseEntity<TaskRequest> getTaskById(UUID id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String user = authentication.getName();

    Optional<Task> task = taskRepository.findById(id);
    if (task == null || !task.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    Optional<User> usr = userRepository.findByEmail(user);
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    Optional<Project> proj = projectRepository.findByIdAndOwner(task.get().getProject().getId(), usr.get().getId());
    if (proj == null || !proj.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(TaskMapper.toJson(task.get()));
  }

  @Override
  @Transactional
  public ResponseEntity<TaskRequest> createTask(TaskRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String user = authentication.getName();

    Optional<User> usr = userRepository.findByEmail(user);
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    if (taskRepository.validateTaskCreation(request.getProjectId(), request.getStatusId(), usr.get().getId()) == 0) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Task task = TaskMapper.toDb(request, usr.get(), request.getStatusId());
    task = taskRepository.save(task);
    
    return ResponseEntity.ok(TaskMapper.toJson(task));
  }

  @Override
  public ResponseEntity<List<TaskRequest>> getAllTasksByProject(UUID projectId) {
    Optional<List<Task>> tsOp = taskRepository.findByProjectId(projectId);
    if (tsOp == null || !tsOp.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(TaskMapper.taskToJson(tsOp.get()));
  }

  @Override
  public ResponseEntity<List<TaskRequest>> getAllTasksByCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String user = authentication.getName();

    Optional<User> usr = userRepository.findByEmail(user);
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    Optional<List<Task>> tsOp = taskRepository.findByAssignedTo(usr.get());
    if (tsOp == null || !tsOp.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(TaskMapper.taskToJson(tsOp.get()));
  }

}
