package com.flowtrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowtrack.controller.request.UserRequest;
import com.flowtrack.controller.response.UserResponse;
import com.flowtrack.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/v1/user")
public class UserController {

  private final UserService userService;

  @GetMapping("")
  public ResponseEntity<UserResponse> getUserByEmail(@RequestBody UserRequest request) {
    ResponseEntity<UserResponse> result = userService.getUserByEmail(request);

    return result;
  }
}
