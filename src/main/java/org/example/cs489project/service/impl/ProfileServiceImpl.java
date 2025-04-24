package org.example.cs489project.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cs489project.dto.request.ProfileRequestDto;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.example.cs489project.exception.custom.UsernameNotFoundException;
import org.example.cs489project.mapper.ProfileMapper;
import org.example.cs489project.model.Profile;
import org.example.cs489project.model.user.User;
import org.example.cs489project.repository.ProfileRespository;
import org.example.cs489project.repository.UserRepository;
import org.example.cs489project.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRespository profileRespository;
    private final ProfileMapper profileMapper;
    private final UserRepository userRepository;

    @Override
    public ProfileResponseDto createProfile(ProfileRequestDto profileRequestDto) {
        Profile profile = profileMapper.profileRequestDtoToProfile(profileRequestDto);
        String username = profile.getUser().getUsername();
        User user = getUserByUsername(username);
        profile.setUser(user);
        profileRespository.save(profile);
        return profileMapper.profileToProfileResponseDto(profile);
    }

    @Override
    @Transactional
    public ProfileResponseDto updateProfile(String username, ProfileRequestDto profileRequestDto) {
        Profile mappedProfile = profileMapper.profileRequestDtoToProfile(profileRequestDto);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            Optional<Profile> optionalProfile = profileRespository.findProfileByUser_Id(existingUser.getId());
            if (optionalProfile.isPresent()) {
                Profile existingProfile = optionalProfile.get();
                mappedProfile.getUser().setId(existingUser.getId());
                mappedProfile.setId(existingProfile.getId());
            }
            Profile updatedProfile = profileRespository.save(mappedProfile);
            return profileMapper.profileToProfileResponseDto(updatedProfile);
        }
        throw new UsernameNotFoundException(username + " not found");
    }

    @Override
    public ProfileResponseDto findProfileByUsername(String username) {
        Profile profile = profileRespository.findProfileByUser_Id(getUserByUsername(username).getId()).get();
        return profileMapper.profileToProfileResponseDto(profile);
    }

    private User getUserByUsername(String username) {
        return (userRepository.findByUsername(username).get());
    }

    @Override
    public Page<ProfileResponseDto> findAllProfiles(
            int page,
            int pageSize,
            String sortDirection,
            String sortBy) {
        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        return profileRespository.findAll(pageable).map(profileMapper::profileToProfileResponseDto);
    }
}
