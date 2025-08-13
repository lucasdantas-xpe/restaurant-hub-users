package com.example.users.api.dto;
import com.example.users.domain.Role;
import jakarta.validation.constraints.*;
public record UserRequest(
  @NotBlank String name,
  @Email @NotBlank String email,
  @NotBlank String login,
  @NotBlank String password,
  String address,
  @NotNull Role role
) {}
