package org.example.cs489project.service;

import org.example.cs489project.dto.request.ProfileRequestDto;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    // Create
    ProfileResponseDto createProfile(ProfileRequestDto profileRequestDto);
    // Update
    ProfileResponseDto updateProfile(String username,ProfileRequestDto profileRequestDto);
    // Find by User
    ProfileResponseDto findProfileByUsername(String username);

    Page<ProfileResponseDto> findAllProfiles( int page,
                                              int pageSize,
                                              String sortDirection,
                                              String sortBy);

}
