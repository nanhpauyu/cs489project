package org.example.cs489project.dto.request;

import java.time.LocalDate;

public record PostRequestDto(
        String content,
        // at
        UserRequestDto user,
        LocalDate date
) {
}
