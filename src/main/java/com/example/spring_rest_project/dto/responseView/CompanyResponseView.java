package com.example.spring_rest_project.dto.responseView;

import com.example.spring_rest_project.dto.response.CompanyResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class CompanyResponseView {
    private List<CompanyResponse> companyResponseList;
    private int currentPage;
    private int totalPage;
}
