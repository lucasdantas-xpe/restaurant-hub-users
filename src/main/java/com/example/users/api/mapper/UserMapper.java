package com.example.users.api.mapper;
import org.mapstruct.*;
import com.example.users.domain.User;
import com.example.users.api.dto.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "passwordHash", source = "password")
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "active", ignore = true)
  User toEntity(UserRequest req);

  UserResponse toResponse(User entity);
}
