package com.example.spring_rest_project.dto.responseView;

import com.example.spring_rest_project.dto.response.CourseResponse;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CourseResponseView {
    List<CourseResponse> responses;
    private int currentPage;
    private int totalPage;
}
