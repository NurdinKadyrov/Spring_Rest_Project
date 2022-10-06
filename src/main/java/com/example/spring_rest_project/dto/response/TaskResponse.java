package com.example.spring_rest_project.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TaskResponse {
    private Long id;
    private String taskName;
    private String taskText;
    private LocalDate deadline;
}
