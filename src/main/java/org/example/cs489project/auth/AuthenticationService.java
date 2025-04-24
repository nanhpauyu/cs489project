package org.example.cs489project.auth;

import lombok.RequiredArgsConstructor;
import org.example.cs489project.config.JwtService;
import org.example.cs489project.exception.custom.DuplicateUsernameExcepiton;
import org.example.cs489project.model.user.User;
import org.example.cs489project.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = new User(
                registerRequest.username(),
                passwordEncoder.encode(registerRequest.password()),
                registerRequest.role()
        );
        Optional<User> userOptional = userRepository.findByUsername(registerRequest.username());
        if (userOptional.isPresent()) {
            throw new DuplicateUsernameExcepiton(registerRequest.username() + "is already in use");
        }
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );
        //generate token for the user
        var user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);


        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }


}
