package org.example.cs489project.repository;

import org.example.cs489project.exception.custom.DuplicateUsernameExcepiton;
import org.example.cs489project.model.user.Role;
import org.example.cs489project.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("nan")
                .password("111")
                .role(Role.ADMIN)
                .build();
    }

    @Test
    @DisplayName("Test for creating a new employee")
    void givenNonExistentUser_whenSave_thenReturnsResponseDto() {
        // Given

        // When
        User savedUser = userRepository.saveAndFlush(user);
        // Then
        assertNotNull(savedUser);
        assertEquals("nan", savedUser.getUsername());

    }

    @Test
    @DisplayName("Test for duplicate username")
    void givenDuplicateUser_whenSave_thenReturnsDuplicateUserException() {
        User savedUser = userRepository.saveAndFlush(user);
        User user1 = User.builder()
                .username("nan")
                .password("111")
                .role(Role.ADMIN)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAndFlush(user1));

    }
}