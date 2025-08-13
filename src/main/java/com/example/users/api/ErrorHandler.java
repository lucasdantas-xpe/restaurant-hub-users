package com.example.users.api;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> validation(MethodArgumentNotValidException ex) {
    return ResponseEntity.badRequest().body(Map.of("error", "Invalid request"));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> generic(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body(Map.of("error", "Request could not be processed"));
  }
}
