package com.example.spring_rest_project.dto.responseView;

import com.example.spring_rest_project.dto.response.VideoResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class VideoResponseView {
    List<VideoResponse> responses;
    private int currentPage;
    private int totalPage;
}
