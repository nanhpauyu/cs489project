package org.example.cs489project.service;

import org.example.cs489project.dto.request.UserRequestDto;
import org.example.cs489project.dto.response.UserResponseDto;

import java.util.Optional;

public interface UserService {
    // Create
    UserResponseDto createUser(UserRequestDto userRequestDto);
    // Find by username
    Optional<UserResponseDto> findByUsername(String username);
    //
}
