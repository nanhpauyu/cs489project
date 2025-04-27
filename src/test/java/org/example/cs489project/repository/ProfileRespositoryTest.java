package org.example.cs489project.repository;

import jakarta.transaction.Transactional;
import org.example.cs489project.model.Profile;
import org.example.cs489project.model.user.Role;
import org.example.cs489project.model.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
@DataJpaTest
class ProfileRespositoryTest {
    @Autowired
    private ProfileRepository profileRespository;

    @Autowired
    private UserRepository userRepository;

    private Profile profile;
    private User user;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .username("nan")
                .password("111")
                .role(Role.MEMBER)
                .build();
        profile = Profile.builder()
                .id(Long.valueOf(1))
                .firstName("Nan")
                .lastName("Shawng")
                .dateOfBirth(LocalDate.of(1994, 1, 1))
                .email("email@email.com")
                .phoneNumber("934341")
                .bio("I am Nan Shawng")
                .build();
    }
    @AfterEach
    public void cleanup() {
        profileRespository.deleteAll();
    }

    @Test
    @Transactional
    public void whenFindProfileByUserId_thenReturnProfile() {
        User savedUser = userRepository.saveAndFlush(user);
        Profile newProfile = Profile.builder()
                .firstName("Nan")
                .lastName("Shawng")
                .build();
        newProfile.setUser(user);
        profileRespository.saveAndFlush(newProfile);
        Profile savedProfile = profileRespository.findProfileByUser_Id(user.getId()).get();
        assertEquals(newProfile.getFirstName(), savedProfile.getFirstName());
    }

    @Test
    public void whenFindProfileByNonExistentUserId_thenReturnEmpty() {
        // When
        Optional<Profile> foundProfile = profileRespository.findProfileByUser_Id(999L);

        // Then
        assertFalse(foundProfile.isPresent());
    }
}