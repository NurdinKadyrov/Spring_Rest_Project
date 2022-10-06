package com.example.spring_rest_project.dto.responseView;

import com.example.spring_rest_project.dto.response.TaskResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class TaskResponseView {
    private List<TaskResponse> responses;
    private int currentPage;
    private int totalPage;
}
