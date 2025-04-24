package org.example.cs489project.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) {
}
