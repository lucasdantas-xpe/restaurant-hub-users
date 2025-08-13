package com.example.users.domain;
import java.util.UUID;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Table("users")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
  @Id private UUID id;
  private String name;
  private String email;
  private String login;
  private String passwordHash;
  private String address;
  private Role role;
  private Instant lastModifiedAt;
  private boolean active;
}
