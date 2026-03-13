package com.flowtrack.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowtrack.controller.request.AuthenticationRequest;
import com.flowtrack.controller.response.AuthenticationResponse;
import com.flowtrack.services.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request, 
          HttpServletResponse response) {
    
    ResponseEntity<AuthenticationResponse> result = authService.register(request);
    if (result.getStatusCode().equals(HttpStatus.OK)) {
      if (result.hasBody()) {
        String token = result.getBody().getToken();
        response.setHeader(HttpHeaders.SET_COOKIE,
        "token=" + token + "; HttpOnly; Secure; SameSite=None; Path=/; Max-Age=604800");
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }      
    } 

    return result;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request,
          HttpServletResponse response) {
    
    ResponseEntity<AuthenticationResponse> result = authService.authenticate(request);
    if (result.getStatusCode().equals(HttpStatus.OK)) {
      if (result.hasBody()) {
        String token = result.getBody().getToken();
        response.setHeader(HttpHeaders.SET_COOKIE,
        "token=" + token + "; HttpOnly; Secure; SameSite=None; Path=/; Max-Age=604800");
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }      
    }   
    return result;
  }
}
