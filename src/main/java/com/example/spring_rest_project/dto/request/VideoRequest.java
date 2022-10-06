package com.example.spring_rest_project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoRequest {
    private String videoName;
    private String link;
    private Long lessonId;
}
