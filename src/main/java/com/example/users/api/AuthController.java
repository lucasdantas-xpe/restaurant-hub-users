package com.example.users.api;
import com.example.users.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserService service;

  public record LoginRequest(@NotBlank String login, @NotBlank String password) {}
  public record ChangePasswordRequest(@NotBlank String currentPassword, @NotBlank String newPassword, @NotBlank String confirmPassword) {}

  @PostMapping("/login")
  public ResponseEntity<Map<String,Object>> login(@Valid @RequestBody LoginRequest req) {
    boolean ok = service.matchesCredentials(req.login(), req.password());
    if (!ok) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                           .body(Map.of("error", "Invalid credentials"));
    }
    return ResponseEntity.ok(Map.of("authenticated", true));
  }

  @PostMapping("/users/{id}/change-password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void change(@PathVariable UUID id, @Valid @RequestBody ChangePasswordRequest req) {
    if (!req.newPassword().equals(req.confirmPassword()))
      throw new RuntimeException("Invalid credentials");
    service.changePassword(id, req.currentPassword(), req.newPassword());
  }
}
