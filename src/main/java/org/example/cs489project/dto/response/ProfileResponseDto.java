package org.example.cs489project.dto.response;

public record ProfileResponseDto(
        UserResponseDto user,
        String firstName,
        String lastName,
//        LocalDate dateOfBirth,
//        String email,
//        String phoneNumber,
        String bio
) {
}
