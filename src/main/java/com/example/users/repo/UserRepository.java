package com.example.users.repo;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import com.example.users.domain.User;
public interface UserRepository extends CrudRepository<User, UUID> {
  Optional<User> findByLogin(String login);
}
