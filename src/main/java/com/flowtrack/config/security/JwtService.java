package com.flowtrack.config.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {
  private final static String SECRET_JWT_KEY = "17IifXPCC1ulTM763mQC9rF3T0gGu3tdLP/gIfXOlgk=";

  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(SECRET_JWT_KEY.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(UserDetails userDetails) {
    return buildToken(new HashMap<>(), userDetails);
  }

  public boolean isTokenValid(String jwt, UserDetails userDetails) {
    final String username = extractUsername(jwt);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt, userDetails);
  }

  public boolean isTokenExpired(String jwt, UserDetails userDetails) {
    return extractClaim(jwt, Claims::getExpiration).before(new Date());
  }

  public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(jwt);
    return claimsResolver.apply(claims);
  }

  public String extractUsername(String jwt) {
    return extractClaim(jwt, Claims::getSubject);
  }

  private String buildToken(HashMap<String, Object> claims, UserDetails userDetails) {
      return Jwts.builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 168))
            .signWith(key, Jwts.SIG.HS256)
            .compact();
  }

  private Claims extractAllClaims(String jwt) {
    return Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(jwt)
          .getPayload();
  }
}
