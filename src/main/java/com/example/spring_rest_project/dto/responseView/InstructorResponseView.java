package com.example.spring_rest_project.dto.responseView;

import com.example.spring_rest_project.dto.response.InstructorResponse;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class InstructorResponseView {
    private List<InstructorResponse> responses;
    private int currentPage;
    private int totalPage;
}
