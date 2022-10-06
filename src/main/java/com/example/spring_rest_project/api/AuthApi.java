package com.example.spring_rest_project.api;

import com.example.spring_rest_project.dto.request.UserRequest;
import com.example.spring_rest_project.dto.response.UserResponse;
import com.example.spring_rest_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class AuthApi {
    private final UserService userService;

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserRequest authRequest) {
        return userService.login(authRequest);
    }


    @PostMapping("/reg")
    public UserResponse create(@RequestBody UserRequest request) {
        return userService.create(request);
    }
}
