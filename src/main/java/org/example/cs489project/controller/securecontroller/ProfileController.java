package org.example.cs489project.controller.securecontroller;

import lombok.RequiredArgsConstructor;
import org.example.cs489project.dto.request.ProfileRequestDto;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.example.cs489project.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final UserDetailsService userDetailsService;

    // Constructor injection
    public ProfileController(ProfileService profileService, UserDetailsService userDetailsService) {
        this.profileService = profileService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getCurrentUserProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        // Get username from authenticated user
        String username = userDetails.getUsername();

        // Find profile by username
        ProfileResponseDto profile = profileService.findProfileByUsername(username);

        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponseDto> getProfileByUsername(
            @PathVariable String username) {

        ProfileResponseDto profile = profileService.findProfileByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDto> createProfile(
            @RequestBody ProfileRequestDto profileRequestDto
    ){
        ProfileResponseDto profileResponseDto = profileService.createProfile(profileRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(profileResponseDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<ProfileResponseDto> updateProfile(
            @PathVariable String username,
            @RequestBody ProfileRequestDto profileRequestDto
    ){
        ProfileResponseDto profileResponseDto = profileService.updateProfile(username, profileRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(profileResponseDto);
    }
}
