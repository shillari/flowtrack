package com.flowtrack.services;

import org.springframework.http.ResponseEntity;

import com.flowtrack.controller.request.AuthenticationRequest;
import com.flowtrack.controller.response.AuthenticationResponse;

public interface AuthenticationService {

  ResponseEntity<AuthenticationResponse> register(AuthenticationRequest request);

  ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
