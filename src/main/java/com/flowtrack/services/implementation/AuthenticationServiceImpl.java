package com.flowtrack.services.implementation;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flowtrack.config.security.JwtService;
import com.flowtrack.controller.request.AuthenticationRequest;
import com.flowtrack.controller.response.AuthenticationResponse;
import com.flowtrack.entity.database.User;
import com.flowtrack.entity.repository.UserRepository;
import com.flowtrack.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public ResponseEntity<AuthenticationResponse> register(AuthenticationRequest request) {
    Optional<User> usr = userRepository.findByEmail(request.getEmail());
    if(usr != null && usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    User newUser = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .activ(Boolean.TRUE)
        .build();

    userRepository.save(newUser);
    String token = jwtService.generateToken(newUser);

    return ResponseEntity.ok(AuthenticationResponse.builder()
        .id(newUser.getId())
        .username(newUser.getUsername())
        .createdAt(newUser.getCreatedAt())
        .token(token)
        .build());
  }

  @Override
  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getEmail(), request.getPassword()));
        
    Optional<User> usr = userRepository.findByEmail(request.getEmail());
    if(usr == null || !usr.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    User user = usr.get();
    String token = jwtService.generateToken(user);

    return ResponseEntity.ok(AuthenticationResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .createdAt(user.getCreatedAt())
        .token(token)
        .build());
  }

}
