package org.example.cs489project.dto.response;

import org.example.cs489project.dto.request.UserRequestDto;

import java.time.LocalDate;

public record PostResponseDto(
        Long id,
        String content,
        UserResponseDto user,
        LocalDate date
) {
}
