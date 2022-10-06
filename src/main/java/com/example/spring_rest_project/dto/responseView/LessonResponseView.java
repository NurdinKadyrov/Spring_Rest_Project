package com.example.spring_rest_project.dto.responseView;

import com.example.spring_rest_project.dto.response.LessonResponse;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonResponseView {
    private List<LessonResponse> responseList;
    private int currentPage;
    private int totalPage;
}
