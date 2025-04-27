package org.example.cs489project.controller.securecontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cs489project.config.JwtService;
import org.example.cs489project.dto.request.ProfileRequestDto;
import org.example.cs489project.dto.request.UserRequestDto;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.example.cs489project.dto.response.UserResponseDto;
import org.example.cs489project.service.ProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cs489project.config.JwtService;
import org.example.cs489project.dto.request.PostRequestDto;
import org.example.cs489project.dto.request.UserRequestDto;
import org.example.cs489project.dto.response.PostResponseDto;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.example.cs489project.dto.response.UserResponseDto;
import org.example.cs489project.service.PostService;
import org.example.cs489project.service.ProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(ProfileController.class)
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    ProfileService profileService;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Create profile")
    void createProfile() throws Exception {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(
                new UserResponseDto("nan"),
                "firstName",
                "lastName",
                LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am"

        );
        ProfileRequestDto profileRequestDto = new ProfileRequestDto(
                new UserRequestDto("nan"),
                "firstName",
                "lastName",
                LocalDate.of(2022,2,2),
                "Email@email.com",
                "03434",
                "bio"
        );
        Mockito.when(profileService.createProfile(profileRequestDto)).thenReturn(profileResponseDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/profiles")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(profileRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(profileResponseDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update profile")
    void updateProfile() throws Exception {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(
                new UserResponseDto("nan"),
                "firstName",
                "lastName",
                LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am"

        );
        ProfileRequestDto profileRequestDto = new ProfileRequestDto(
                new UserRequestDto("nan"),
                "firstName",
                "lastName",
                LocalDate.of(2022,2,2),
                "Email@email.com",
                "03434",
                "update"
        );
        Mockito.when(profileService.updateProfile("nan",profileRequestDto)).thenReturn(profileResponseDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/profiles/{username}","nan")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(profileRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(profileResponseDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}