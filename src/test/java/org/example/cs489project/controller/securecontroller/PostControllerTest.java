package org.example.cs489project.controller.securecontroller;

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

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get All Posts")
    @WithMockUser(roles = "ADMIN")
    void getAllPosts() throws Exception {
        int page = 0;
        int pageSize = 5;
        String sortDirection = "asc";
        String sortBy = "id";
        PostResponseDto postResponseDto = new PostResponseDto(
                1l,
                "Post 1",
                new UserResponseDto("nan"),
                LocalDate.of(2022,2,2)
        );
        PostResponseDto postResponseDto2 = new PostResponseDto(
                2l,
                "Post 1",
                new UserResponseDto("nan"),
                LocalDate.of(2022,2,2)
        );
        Page<PostResponseDto> mockPage = new PageImpl<>(Arrays.asList(postResponseDto, postResponseDto2));
        Mockito.when(postService.findPostsByUsername("nan",
                page,pageSize,sortDirection,sortBy
        )).thenReturn(mockPage);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/posts")
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Post mapping test")
    @WithMockUser(roles = "ADMIN")
    void postMappingTest() throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto(
                "Post1",
                new UserRequestDto("nan"),
                LocalDate.of(2022,2,2)
        );
        PostResponseDto postResponseDto = new PostResponseDto(
                1l,
                "Post1",
                new UserResponseDto("nan"),
                LocalDate.of(2022,2,2)
        );

        Mockito.when(postService.createPost(postRequestDto)).thenReturn(postResponseDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/posts")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(postResponseDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());


    }


    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete Post")
    void givenExistingEmail_deleteEmployee_thenDeleted() throws Exception {
        Mockito.doNothing().when(postService).deletePostByPostId("nan",1l);
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/posts/{id}", 1l).with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }


}