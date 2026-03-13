package com.flowtrack.services.implementation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.flowtrack.controller.request.TaskStatusRequest;
import com.flowtrack.controller.request.TaskStatusSwapRequest;
import com.flowtrack.controller.response.TaskStatusResponse;
import com.flowtrack.entity.database.Project;
import com.flowtrack.entity.database.TaskStatus;
import com.flowtrack.entity.database.User;
import com.flowtrack.entity.mapper.TaskStatusMapper;
import com.flowtrack.entity.repository.ProjectRepository;
import com.flowtrack.entity.repository.TaskStatusRepository;
import com.flowtrack.entity.repository.UserRepository;
import com.flowtrack.services.TaskStatusService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

  private final TaskStatusRepository taskStatusRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  @Override
  public ResponseEntity<TaskStatusResponse> createTaskStatus(TaskStatusRequest request) {
    
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String user = authentication.getName();

    Optional<User> usr = userRepository.findByEmail(user);
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    Optional<Project> proj = projectRepository.findByIdAndOwner(request.getProjectId(), usr.get().getId());
    if (proj == null || proj.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    BigDecimal result = projectRepository.getMaxPosition();
    BigDecimal position = (result == null ? BigDecimal.ONE : result.add(new BigDecimal(1)));
    request.setPosition(position);

    TaskStatus status = taskStatusRepository.save(TaskStatusMapper.toDb(request, proj.get()));

    return ResponseEntity.ok(TaskStatusMapper.toJson(status));
  }

  @Override
  public ResponseEntity<List<TaskStatusResponse>> getTaskStatusByProjectId(UUID projectId) {

    Optional<Project> proj = projectRepository.findById(projectId);
    if (proj == null || !proj.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    List<TaskStatus> statuses = taskStatusRepository.findAllByProjectIdOrderByPositionAsc(projectId);
    if (statuses == null || statuses.isEmpty()) return ResponseEntity.ok().build();

    return ResponseEntity.ok(TaskStatusMapper.listToJson(statuses));
  }

  @Override
  @Transactional
  public ResponseEntity<TaskStatusSwapRequest> swapTaskStatusPosition(TaskStatusSwapRequest request) {
    
    taskStatusRepository.setNewPosition(request.getId1(), request.getPosition1());
    taskStatusRepository.setNewPosition(request.getId2(), request.getPosition2());
    
    return ResponseEntity.ok(request);
  }

}
