package org.example.cs489project.service.impl;

import org.example.cs489project.dto.request.PostRequestDto;
import org.example.cs489project.dto.request.UserRequestDto;
import org.example.cs489project.dto.response.PostResponseDto;
import org.example.cs489project.dto.response.UserResponseDto;
import org.example.cs489project.mapper.PostMapper;
import org.example.cs489project.model.Post;
import org.example.cs489project.model.user.User;
import org.example.cs489project.repository.PostRepository;
import org.example.cs489project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    private PostRequestDto postRequestDto;
    private Post post;
    private User user;
    private PostResponseDto postResponseDto;

    @BeforeEach
    void setUp() {
        user = new User("nan");
        postRequestDto = new PostRequestDto(
                "Content",
                new UserRequestDto("nan"),
                LocalDate.of(2022, 2, 2)
        );
        post = new Post(
                "Content",
                user,
                LocalDate.of(2022, 2, 2)
        );
        postResponseDto = new PostResponseDto(
                1L,
                "Content",
                new UserResponseDto("user")
                , LocalDate.of(2022, 2, 2)
        );

    }

    @Test
    @DisplayName("Create Post")
    void createPost() {
        Mockito.when(postMapper.postRequestDtoToPost(Mockito.any(PostRequestDto.class))).thenReturn(post);
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
        Mockito.when(postMapper.postToPostResponseDto(Mockito.any(Post.class))).thenReturn(postResponseDto);
        PostResponseDto result = postService.createPost(postRequestDto);
        assertNotNull(result);
        assertEquals(postResponseDto.content(), result.content());
    }

    @Test
    @DisplayName("Update post")
    void updatePost() {
        Post updatedPost = new Post(
                "Update Content",user,LocalDate.of(2022,2,2)
        );
        PostResponseDto updatedPostResponseDto = new PostResponseDto(
                2l,"Update Content",new UserResponseDto("nan"),LocalDate.of(2022,2,2)
        );
        Mockito.when(postMapper.postRequestDtoToPost(Mockito.any(PostRequestDto.class))).thenReturn(post);
        Mockito.when(postMapper.postToPostResponseDto(Mockito.any(Post.class))).thenReturn(updatedPostResponseDto);
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(post));
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(updatedPost);

        PostResponseDto result = postService.updatePost("nan",1l,postRequestDto);

        assertNotNull(result);
        assertEquals(updatedPostResponseDto.content(), result.content());
    }

    @Test
    @DisplayName("Delete post by id")
    void deletePostById() {
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(post));
        Mockito.doNothing().when(postRepository).deleteById(Mockito.anyLong());
        postService.deletePostByPostId("nan",1l);
        Mockito.verify(postRepository, Mockito.times(1)).deleteById(Mockito.anyLong());

    }

}