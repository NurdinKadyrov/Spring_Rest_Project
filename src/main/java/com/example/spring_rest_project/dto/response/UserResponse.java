package com.example.spring_rest_project.dto.response;

import com.example.spring_rest_project.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserResponse {
  private String email;
  private String token;
  private Role role;
}
