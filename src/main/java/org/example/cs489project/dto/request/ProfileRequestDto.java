package org.example.cs489project.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProfileRequestDto(
        @Valid
        UserRequestDto user,
        @NotBlank(message = "FirstName cannot be blank/empty/null")
        String firstName,
        @NotBlank(message = "LastName cannot be blank/empty/null")
        String lastName,
        @NotNull(message = "DOB cannot be null")
        LocalDate dateOfBirth,
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "PhoneNumber cannot be blank/empty/null")
        String phoneNumber,
        @NotBlank(message = "Bio cannot be blank/empty/null")
        String bio
) {
}
