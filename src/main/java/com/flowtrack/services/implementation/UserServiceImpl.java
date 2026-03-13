package com.flowtrack.services.implementation;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flowtrack.controller.request.UserRequest;
import com.flowtrack.controller.response.UserResponse;
import com.flowtrack.entity.database.User;
import com.flowtrack.entity.repository.UserRepository;
import com.flowtrack.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public ResponseEntity<UserResponse> getUserByEmail(UserRequest request) {
    
    Optional<User> usr = userRepository.findByEmail(request.getEmail());
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    User user = usr.get();

    return ResponseEntity.ok(UserResponse.builder()
            .email(user.getEmail())
            .id(user.getId())
            .username(user.getUsername())
            .createdAt(user.getCreatedAt())
            .build());
  }


}
