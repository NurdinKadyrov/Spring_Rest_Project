package com.example.spring_rest_project.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {
    private String taskName;
    private String taskText;
    private LocalDate deadline;
    private Long lessonId;
}

