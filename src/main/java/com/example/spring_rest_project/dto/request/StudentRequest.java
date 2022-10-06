package com.example.spring_rest_project.dto.request;

import com.example.spring_rest_project.entities.enums.StudyFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private StudyFormat studyFormat;
    private String password;
    private Long companyId;
    private String email;}
