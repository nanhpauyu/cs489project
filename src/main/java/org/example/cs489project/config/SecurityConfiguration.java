package org.example.cs489project.config;

import lombok.RequiredArgsConstructor;
import org.example.cs489project.model.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(
//                CsrfConfigurer::disable
//        ).authorizeHttpRequests(
//                authorizeRequests -> authorizeRequests.requestMatchers("/api/v1/auth/*").permitAll()
//                        .anyRequest()
//                        .authenticated()
//        ).build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/api/v1/auth/*").permitAll()
                                .requestMatchers("/api/v1/management/**").hasAnyRole(Role.ADMIN.name(), Role.MEMBER.name())
                                .anyRequest().authenticated()
                )
                //we do not want Spring security context to store anything related with session
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )              .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}


