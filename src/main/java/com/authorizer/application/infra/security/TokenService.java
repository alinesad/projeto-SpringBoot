/*
 * Responsible for generating and validating the token
 */
package com.authorizer.application.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.authorizer.application.domain.user.User;

/**
 * Responsible for generating and validating the token
 * 
 * @author Aline Divino
 *
 */
@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;


  public String generateToken(User user) {
    try {
      var algoritmo = Algorithm.HMAC256(secret);
      return JWT.create().withIssuer("API For User Authentication").withSubject(user.getLogin())
          .withExpiresAt(dataExpiracao())
          .sign(algoritmo);
    } catch (JWTCreationException exception) {
      throw new JWTCreationException("Error generating jwt token", exception);
    }
  }

  public String getSubject(String tokenJWT) {
    try {
      var algoritmo = Algorithm.HMAC256(secret);
      return JWT.require(algoritmo).withIssuer("API For User Authentication").build()
          .verify(tokenJWT).getSubject();
    } catch (JWTVerificationException exception) {
      throw new JWTVerificationException("Invalid or expired JWT token!");
    }
  }

  /**
   * 
   * @return LocalDateTime + 2 hours
   */
  private Instant dataExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

}
