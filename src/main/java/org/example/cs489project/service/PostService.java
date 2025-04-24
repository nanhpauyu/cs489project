package org.example.cs489project.service;

import org.example.cs489project.dto.request.PostRequestDto;
import org.example.cs489project.dto.response.PostResponseDto;
import org.example.cs489project.model.Profile;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface PostService {
    // Create
    PostResponseDto createPost(PostRequestDto postRequestDto);
    // Update
    PostResponseDto updatePost(String username,Long id,PostRequestDto postRequestDto);
    // Delete
    void deletePostByPostId(String username, Long postId);
    // Get all post by user
    Page<PostResponseDto> findPostsByUsername(String username,int page, int pageSize, String sortDirection, String sortBy);
    // Get all post
    List<PostResponseDto> findAllPosts();
    PostResponseDto findPostById(Long id);
}
