package com.example.spring_rest_project.dto.responseView;

import com.example.spring_rest_project.dto.response.StudentResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class StudentResponseView {
    private List<StudentResponse> responses;
    private int currentPage;
    private int totalPage;
}
