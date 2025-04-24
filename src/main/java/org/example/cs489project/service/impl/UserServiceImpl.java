package org.example.cs489project.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cs489project.dto.request.UserRequestDto;
import org.example.cs489project.dto.response.UserResponseDto;
import org.example.cs489project.exception.custom.DuplicateUsernameExcepiton;
import org.example.cs489project.mapper.UserMapper;
import org.example.cs489project.model.user.Role;
import org.example.cs489project.model.user.User;
import org.example.cs489project.repository.UserRepository;
import org.example.cs489project.service.ProfileService;
import org.example.cs489project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.userRequestDtoToUser(userRequestDto);
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw new DuplicateUsernameExcepiton("username "+ user.getUsername()+" exist");
        }
        user.setRole(Role.MEMBER);
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public Optional<UserResponseDto> findByUsername(String username) {
        return Optional.empty();
    }
}
