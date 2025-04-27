package org.example.cs489project.service.impl;

import org.example.cs489project.dto.request.ProfileRequestDto;
import org.example.cs489project.dto.request.UserRequestDto;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.example.cs489project.dto.response.UserResponseDto;
import org.example.cs489project.mapper.ProfileMapper;
import org.example.cs489project.model.Profile;
import org.example.cs489project.model.user.Role;
import org.example.cs489project.model.user.User;
import org.example.cs489project.repository.ProfileRepository;
import org.example.cs489project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private User user;
    private Profile profile;
    private ProfileRequestDto profileRequestDto;
    private UserRequestDto userRequestDto;
    private ProfileResponseDto profileResponseDto;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        user = new User(1l,"nan","123", Role.MEMBER);
        profile = new Profile(user,"nan","shawng", LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am");
        userRequestDto = new UserRequestDto("nan");
        profileRequestDto = new ProfileRequestDto(userRequestDto,"nan","shawng", LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am");
        userResponseDto = new UserResponseDto("nan");
        profileResponseDto = new ProfileResponseDto(userResponseDto,"nan","shawng",LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am");
    }

    @Test
    @DisplayName("Create profile")
    void createProfile() {
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(profile);
        Mockito.when(profileMapper.profileToProfileResponseDto(Mockito.any())).thenReturn(profileResponseDto);
        Mockito.when(profileMapper.profileRequestDtoToProfile(Mockito.any())).thenReturn(profile);
//        Mockito.when(profileRespository.findProfileByUser_Id(Mockito.any())).thenReturn(Optional.of(profile));
        ProfileResponseDto testProfileResponseDto = profileService.createProfile(profileRequestDto);

        assertNotNull(testProfileResponseDto);
        assertEquals(profileResponseDto.firstName(), testProfileResponseDto.firstName());
        Mockito.verify(profileRepository, Mockito.times(1)).save(Mockito.any(Profile.class));
        Mockito.verify(profileMapper, Mockito.times(1)).profileToProfileResponseDto(Mockito.any());

    }

    @Test
    @DisplayName("Find all profiles with pagination - success")
    void findAllProfiles_ShouldReturnPagedResults() {
        // Arrange
        int page = 0;
        int pageSize = 10;
        String sortDirection = "ASC";
        String sortBy = "id";

        // Create test data
        List<Profile> mockProfiles = Arrays.asList(
                new Profile(new User(),"nan","shawng", LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am"),
                new Profile(new User(),"shawng","nan", LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am")
        );

        Page<Profile> mockPage = new PageImpl<>(mockProfiles);

        List<ProfileResponseDto> mockDtos = Arrays.asList(
                new ProfileResponseDto(new UserResponseDto("nan"),"nan","shawng",LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am"),
                new ProfileResponseDto(new UserResponseDto("shawng"),"shawng","nan",LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am")
        );

        // Mock repository and mapper behavior
        Mockito.when(profileRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(mockPage);

        Mockito.when(profileMapper.profileToProfileResponseDto(mockProfiles.get(0)))
                .thenReturn(mockDtos.get(0));

        Mockito.when(profileMapper.profileToProfileResponseDto(mockProfiles.get(1)))
            .thenReturn(mockDtos.get(1));

        // Act
        Page<ProfileResponseDto> result = profileService.findAllProfiles(
                page, pageSize, sortDirection, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(mockDtos, result.getContent());

        // Verify repository call with correct pagination
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        Mockito.verify(profileRepository).findAll(pageableCaptor.capture());

        PageRequest capturedPageable = (PageRequest) pageableCaptor.getValue();
        assertEquals(page, capturedPageable.getPageNumber());
        assertEquals(pageSize, capturedPageable.getPageSize());
        assertEquals(Sort.Direction.ASC, capturedPageable.getSort().getOrderFor(sortBy).getDirection());
    }


    @Test
    @DisplayName("Find all profiles with empty result")
    void findAllProfiles_ShouldHandleEmptyResult() {
        // Arrange
        Page<Profile> emptyPage = new PageImpl<>(Collections.emptyList());
        Mockito.when(profileRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(emptyPage);

        // Act
        Page<ProfileResponseDto> result = profileService.findAllProfiles(
                0, 10, "ASC", "lastName");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        Mockito.verify(profileMapper, Mockito.never()).profileToProfileResponseDto(Mockito.any());
    }

    @Test
    @DisplayName("Find profile by username - success")
    void findProfileByUsername_Success() {
        // Arrange
        String username = "testuser";
        Long userId = 1L;

        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setUsername(username);

        Profile mockProfile = new Profile();
        mockProfile.setId(1L);
        mockProfile.setUser(mockUser);

        ProfileResponseDto expectedDto = new ProfileResponseDto(
                new UserResponseDto("testuser"),"nan","shawng",LocalDate.of(2022,2,2),"ee@gmail.com","934134","I am"
        );

        // Mock repository responses
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(mockUser));
        Mockito.when(profileRepository.findProfileByUser_Id(userId))
                .thenReturn(Optional.of(mockProfile));
        Mockito.when(profileMapper.profileToProfileResponseDto(mockProfile))
                .thenReturn(expectedDto);

        // Act
        ProfileResponseDto result = profileService.findProfileByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto, result);
        assertEquals(username, result.user().username());

        // Verify interactions
        Mockito.verify(userRepository).findByUsername(username);
        Mockito.verify(profileRepository).findProfileByUser_Id(userId);
        Mockito.verify(profileMapper).profileToProfileResponseDto(mockProfile);
    }

}
