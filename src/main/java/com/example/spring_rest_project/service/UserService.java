package com.example.spring_rest_project.service;

import com.example.spring_rest_project.dto.request.UserRequest;
import com.example.spring_rest_project.dto.response.UserResponse;
import com.example.spring_rest_project.entities.User;
import com.example.spring_rest_project.repositoty.UserRepository;
import com.example.spring_rest_project.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserResponse login(UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = repository.findByEmail(authentication.getName()).orElseThrow(
                () -> new BadCredentialsException("bad credentials!"));

        return new UserResponse(user.getEmail(), jwtTokenUtil.generateToken(user.getEmail()), user.getRole());
    }

    public UserResponse create(UserRequest userRequest) {
        User user = mapToEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return mapToResponse(repository.save(user));
    }

    public User mapToEntity(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .role(request.getRole())
                .isActive(true)
                .createdDate(LocalDate.now())
                .build();
    }

    public UserResponse mapToResponse(User user) {
        String token = jwtTokenUtil.generateToken(user.getEmail());
        return UserResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
}
