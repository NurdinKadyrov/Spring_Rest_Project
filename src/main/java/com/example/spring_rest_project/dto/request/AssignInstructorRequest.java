package com.example.spring_rest_project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignInstructorRequest {
    private Long courseId;
    private Long instructorId;
}
