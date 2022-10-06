package com.example.spring_rest_project.dto.response;

import com.example.spring_rest_project.entities.enums.StudyFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private StudyFormat studyFormat;
    private String email;
}
