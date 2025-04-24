package org.example.cs489project.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cs489project.dto.request.PostRequestDto;
import org.example.cs489project.dto.response.PostResponseDto;
import org.example.cs489project.exception.custom.PostNotFoundException;
import org.example.cs489project.exception.custom.UsernameNotFoundException;
import org.example.cs489project.mapper.PostMapper;
import org.example.cs489project.model.Post;
import org.example.cs489project.model.user.User;
import org.example.cs489project.repository.PostRepository;
import org.example.cs489project.repository.UserRepository;
import org.example.cs489project.service.PostService;
import org.example.cs489project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = postMapper.postRequestDtoToPost(postRequestDto);
        User user = post.getUser();
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            user.setId(optionalUser.get().getId());
            postRepository.save(post);
            return postMapper.postToPostResponseDto(post);
        }
        throw new UsernameNotFoundException(user.getUsername() + "is not found");
    }

    @Override
    public PostResponseDto updatePost(String username, Long id, PostRequestDto postRequestDto) {
        Post mappedPost = postMapper.postRequestDtoToPost(postRequestDto);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            mappedPost.getUser().setId(optionalUser.get().getId());
            Optional<Post> optionalPost = postRepository.findById(id);
            if (optionalPost.isPresent()) {
                mappedPost.setId(optionalPost.get().getId());
                postRepository.save(mappedPost);
                return postMapper.postToPostResponseDto(mappedPost);
            }
            throw new PostNotFoundException("Post id " + id + " not found");
        }
        throw new UsernameNotFoundException(username + "is not found");
    }

    @Override
    public void deletePostByPostId(String username, Long postId) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            Optional<Post> optionalPost = postRepository.findById(postId);
            if (optionalPost.get().getUser() == optionalUser.get()) {
                postRepository.delete(optionalPost.get());
            } else {
                throw new PostNotFoundException("Post id " + postId + " not found");
            }
        } else {
            throw new UsernameNotFoundException(username + "is not found");
        }
    }

    @Override
    public Page<PostResponseDto> findPostsByUsername(
            String username,
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
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            Page<Post> posts = postRepository.findPostsByUser_Id(optionalUser.get().getId(), pageable);
            return posts.map(postMapper::postToPostResponseDto);
        }
        throw new UsernameNotFoundException(username + "is not found");
    }

    @Override
    public List<PostResponseDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return postMapper.postsToPostResponseDtos(posts);
    }

    @Override
    public PostResponseDto findPostById(Long id) {
        return postMapper.postToPostResponseDto(postRepository.findById(id).get());
    }
}
