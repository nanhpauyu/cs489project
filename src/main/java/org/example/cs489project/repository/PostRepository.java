package org.example.cs489project.repository;

import org.example.cs489project.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByUser_Id(Long userId);
    Page<Post> findPostsByUser_Id(Long userId, Pageable pageable);
}
