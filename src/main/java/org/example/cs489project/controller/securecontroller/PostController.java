package org.example.cs489project.controller.securecontroller;


import lombok.RequiredArgsConstructor;
import org.example.cs489project.dto.request.PostRequestDto;
import org.example.cs489project.dto.response.PostResponseDto;
import org.example.cs489project.service.PostService;
import org.example.cs489project.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final UserDetailsService userDetailsService;

    public PostController(PostService postService, UserDetailsService userDetailsService) {
        this.postService = postService;
        this.userDetailsService = userDetailsService;
    }

    // Get all
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int pageSize,
            @RequestParam(defaultValue = "asc")
            String sortDirection,
            @RequestParam(defaultValue = "date")
            String sortBy
    ) {
        String username = userDetails.getUsername();
        Page<PostResponseDto> allPosts = postService.findPostsByUsername(username,page, pageSize,sortDirection,sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(allPosts);
    }

    // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto post = postService.findPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    // Create
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDto));
    }

    // Put
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(username, id, postRequestDto));

    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        postService.deletePostByPostId(username, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
