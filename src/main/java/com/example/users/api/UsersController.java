package com.example.users.api;
import com.example.users.api.dto.*;
import com.example.users.api.mapper.UserMapper;
import com.example.users.domain.User;
import com.example.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
  private final UserService service;
  private final UserMapper mapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse create(@Valid @RequestBody UserRequest req) {
    User u = mapper.toEntity(req);
    u.setPasswordHash(req.password()); // will be hashed in the service
    return mapper.toResponse(service.create(u));
  }

  @GetMapping
  public List<UserResponse> list() {
    var iterable = service.list(); // Iterable<User>
    return StreamSupport.stream(iterable.spliterator(), false)
        .map(mapper::toResponse)
        .toList();
  }

  @GetMapping("/{id}")
  public UserResponse get(@PathVariable UUID id) {
    return mapper.toResponse(service.get(id));
  }

  @PutMapping("/{id}")
  public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UserRequest req) {
    User u = mapper.toEntity(req);
    return mapper.toResponse(service.update(id, u));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
