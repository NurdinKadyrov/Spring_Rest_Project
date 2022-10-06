package com.example.spring_rest_project.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LessonResponse {
    private Long id;
    private String lessonName;
}