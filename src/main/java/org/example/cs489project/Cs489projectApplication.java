package org.example.cs489project;

import lombok.RequiredArgsConstructor;
import org.example.cs489project.dto.request.PostRequestDto;
import org.example.cs489project.dto.request.ProfileRequestDto;
import org.example.cs489project.dto.request.UserRequestDto;
import org.example.cs489project.model.user.User;
import org.example.cs489project.service.PostService;
import org.example.cs489project.service.ProfileService;
import org.example.cs489project.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class Cs489projectApplication {
    private final UserService userService;
    private final ProfileService profileService;
    private final PostService postService;
    public static void main(String[] args) {
        SpringApplication.run(Cs489projectApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }

}
