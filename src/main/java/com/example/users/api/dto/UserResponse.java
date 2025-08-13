package com.example.users.api.dto;
import com.example.users.domain.Role;
import java.util.UUID;
public record UserResponse(UUID id, String name, String email, String login, String address, Role role) {}
