package com.example.users.service;
import java.time.Instant;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.users.api.dto.UserRequest;
import com.example.users.domain.User;
import com.example.users.repo.UserRepository;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class UserService {
  private final UserRepository repo;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public User create(User u) {
    u.setId(UUID.randomUUID());
    u.setPasswordHash(encoder.encode(u.getPasswordHash())); // already hashed at this point
    u.setLastModifiedAt(Instant.now());
    u.setActive(true);
    return repo.save(u);
  }

  public User update(UUID id, User update) {
    var u = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    u.setName(update.getName());
    u.setEmail(update.getEmail());
    u.setAddress(update.getAddress());
    u.setLastModifiedAt(Instant.now());
    u.setRole(update.getRole());
    return repo.save(u);
  }

  public void delete(UUID id) { repo.deleteById(id); }

  public Iterable<User> list() { return repo.findAll(); }

  public User get(UUID id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found")); }

  public boolean matchesCredentials(String login, String rawPassword) {
    return repo.findByLogin(login)
      .map(u -> encoder.matches(rawPassword, u.getPasswordHash()))
      .orElse(false);
  }

  public void changePassword(UUID id, String current, String newPwd) {
    var u = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    if (!encoder.matches(current, u.getPasswordHash())) throw new RuntimeException("Invalid credentials");
    u.setPasswordHash(encoder.encode(newPwd));
    u.setLastModifiedAt(Instant.now());
    repo.save(u);
  }
}
