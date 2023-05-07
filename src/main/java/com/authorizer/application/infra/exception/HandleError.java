/*
 * Responsible for Handling Error
 * 
 */
package com.authorizer.application.infra.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.persistence.EntityNotFoundException;

/**
 * Responsible for Handling Error and
 * isolate API exception handling
 * @author Aline Divino
 *
 */
@RestControllerAdvice
public class HandleError {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> tratarError404() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ValidationErrorData>> handleError400(MethodArgumentNotValidException ex) {

    List<FieldError> errors = ex.getFieldErrors();

    return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorData::new).toList());
  }
  
  
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> handleErrorValidation(ValidationException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
  

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleHttpMessageNotReadable(Exception ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleError500(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Internal Error: " + ex.getLocalizedMessage());
  }
  

  @ExceptionHandler(InternalAuthenticationServiceException.class)
  public ResponseEntity<String> handleInternalAuthenticationService() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
  }
  
  @ExceptionHandler(JWTCreationException.class)
  public ResponseEntity<String> handleJWTCreation() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error generating jwt token");
  }
  
  
  @ExceptionHandler(JWTVerificationException.class)
  public ResponseEntity<String> handleJWTVerification() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired JWT token!");
  }
  

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleErrorAccessDenied() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
  }

  private record ValidationErrorData(String field, String message) {

    public ValidationErrorData(FieldError error) {
      this(error.getField(), error.getDefaultMessage());

    }

  }

}
