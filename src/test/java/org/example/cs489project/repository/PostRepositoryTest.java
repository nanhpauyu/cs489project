package org.example.cs489project.repository;

import org.example.cs489project.model.Post;
import org.example.cs489project.model.Profile;
import org.example.cs489project.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private Post post;
    private Profile profile;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("nan").password("124").build();
        userRepository.save(user);
        profile = Profile.builder().firstName("Nan").lastName("Nan").build();
        profileRepository.save(profile);
        post = Post.builder().content("Hello World").build();
    }
    // creating test
    @Test
    @DisplayName("Creating post")
    public void createPost() {
        Post savePost = postRepository.save(post);
        assertNotNull(savePost.getId());
        assertEquals(post.getContent(), savePost.getContent());
    }
    // deleting test
    @Test
    @DisplayName("Delete post")
    public void deletePost() {
        Post savePost = postRepository.save(post);
        assertNotNull(savePost.getId());
        Long savePostId = savePost.getId();
        postRepository.delete(savePost);
        Optional<Post> deletedPost = postRepository.findById(savePostId);
        assertFalse(deletedPost.isPresent(), "Post should not exist after deletion");
    }
    // finding test
    @Test
    @DisplayName("Find by id")
    public void findPostById() {
        Post savePost = postRepository.save(post);
        assertNotNull(savePost.getId());
        Post retrievedPost = postRepository.findById(savePost.getId()).get();
        assertEquals(savePost.getContent(), retrievedPost.getContent());
    }
    // finding post by user id
    @Test
    @DisplayName("Find post by user id")
    public void findPostByUserId() {
        post.setUser(user);
        Post savePost = postRepository.save(post);
        assertNotNull(savePost.getId());
        System.out.println(user.getId());
        List<Post> retrievedPosts = postRepository.findPostsByUser_Id(user.getId());
        assertEquals(1, retrievedPosts.size());
    }
}