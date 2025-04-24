package org.example.cs489project.repository;

import org.example.cs489project.model.Profile;
import org.example.cs489project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRespository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileByUser_Id(Long userId);
}
