package org.example.cs489project.controller.securecontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cs489project.config.JwtService;
import org.example.cs489project.config.SecurityConfiguration;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.example.cs489project.dto.response.UserResponseDto;
import org.example.cs489project.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;
    @MockitoBean
    private JwtService jwtService;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Get all profiles - should return paginated profiles successfully")
    void getAllProfiles_Success() throws Exception {
//        // Arrange
        int page = 0;
        int pageSize = 5;
        String sortDirection = "asc";
        String sortBy = "firstName";
        ProfileResponseDto profileResponseDto1 = new ProfileResponseDto(
                new UserResponseDto("nan"),
                "Nan",
                "Shawng",
                LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am"
        );
        ProfileResponseDto profileResponseDto2 = new ProfileResponseDto(
                new UserResponseDto("hpau"),
                "Hpau",
                "Yu",
                LocalDate.of(1994,1,1),"ee@gmail.com","934134","I am"
        );
        Page<ProfileResponseDto> mockPage = new PageImpl<>(Arrays.asList(profileResponseDto1,profileResponseDto2));
        Mockito.when(profileService.findAllProfiles(
                page,pageSize,sortDirection,sortBy
        )).thenReturn(mockPage);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/admin")
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        );
    }
}
