package org.example.cs489project.controller.securecontroller;

import lombok.RequiredArgsConstructor;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.example.cs489project.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

private final ProfileService profileService;
    @GetMapping
    public ResponseEntity<Page<ProfileResponseDto>> getAllProfiles(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int pageSize,
            @RequestParam(defaultValue = "asc")
            String sortDirection,
            @RequestParam(defaultValue = "id")
            String sortBy
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.findAllProfiles(page, pageSize, sortDirection, sortBy));
    }
}


