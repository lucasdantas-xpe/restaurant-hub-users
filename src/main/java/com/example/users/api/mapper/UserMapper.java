package com.example.users.api.mapper;
import org.mapstruct.*;
import com.example.users.domain.User;
import com.example.users.api.dto.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User toEntity(UserRequest req);
  UserResponse toResponse(User entity);
}
