package com.example.spring_rest_project.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class CourseResponse {
    private Long id;
    private String courseName;
    private LocalDate dateOfStart;
    private String duration;
    private String description;
}
