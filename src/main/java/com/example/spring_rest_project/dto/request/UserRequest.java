package com.example.spring_rest_project.dto.request;

import com.example.spring_rest_project.entities.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private Role role;

}
