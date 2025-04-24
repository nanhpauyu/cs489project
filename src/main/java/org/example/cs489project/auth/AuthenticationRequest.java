package org.example.cs489project.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AuthenticationRequest(
        String username,
        String password
) {
}
