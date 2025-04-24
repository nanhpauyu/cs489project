package org.example.cs489project.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.example.cs489project.model.user.Role;

public record RegisterRequest(
        @NotBlank(message = "username cannot be blank/empty/null")
        String username,
        @NotBlank(message = "password cannot be blank/empty/null")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[?*+!])[A-Za-z0-9?*+!]{4,8}$")
        String password,
        Role role) {
}
