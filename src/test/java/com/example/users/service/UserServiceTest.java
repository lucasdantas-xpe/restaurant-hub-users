package com.example.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import com.example.users.domain.User;
import com.example.users.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository repo;

  @InjectMocks
  private UserService service;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Test
  void matchesCredentialsReturnsTrue() {
    var user = User.builder()
        .id(UUID.randomUUID())
        .login("john")
        .passwordHash(encoder.encode("pwd"))
        .build();
    when(repo.findByLogin("john")).thenReturn(Optional.of(user));

    assertTrue(service.matchesCredentials("john", "pwd"));
  }

  @Test
  void matchesCredentialsReturnsFalseWhenUserMissing() {
    when(repo.findByLogin("john")).thenReturn(Optional.empty());

    assertFalse(service.matchesCredentials("john", "pwd"));
  }

  @Test
  void changePasswordUpdatesHash() {
    UUID id = UUID.randomUUID();
    var user = User.builder()
        .id(id)
        .passwordHash(encoder.encode("old"))
        .build();
    when(repo.findById(id)).thenReturn(Optional.of(user));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

    service.changePassword(id, "old", "newPwd");

    verify(repo).save(argThat(saved -> encoder.matches("newPwd", saved.getPasswordHash())));
  }

  @Test
  void changePasswordThrowsWhenCurrentInvalid() {
    UUID id = UUID.randomUUID();
    var user = User.builder()
        .id(id)
        .passwordHash(encoder.encode("old"))
        .build();
    when(repo.findById(id)).thenReturn(Optional.of(user));

    assertThrows(RuntimeException.class, () -> service.changePassword(id, "wrong", "new"));
    verify(repo, never()).save(any());
  }
}

