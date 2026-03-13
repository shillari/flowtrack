package com.flowtrack.services;

import org.springframework.http.ResponseEntity;

import com.flowtrack.controller.request.UserRequest;
import com.flowtrack.controller.response.UserResponse;

public interface UserService {

  ResponseEntity<UserResponse> getUserByEmail(UserRequest request);
}
