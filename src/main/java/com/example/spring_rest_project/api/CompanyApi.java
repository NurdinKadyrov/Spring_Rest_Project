package com.example.spring_rest_project.api;

import com.example.spring_rest_project.dto.request.CompanyRequest;
import com.example.spring_rest_project.dto.response.CompanyResponse;
import com.example.spring_rest_project.dto.responseView.CompanyResponseView;
import com.example.spring_rest_project.service.CompanyServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/company")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "company api", description = "Admin can create")
public class CompanyApi {

    private final CompanyServices companyServices;



    @GetMapping("/{id}")
    @Operation(description = "ADMIN can get by id")
    public CompanyResponse getById(@PathVariable Long id) {
        return companyServices.getById(id);
    }

    @PostMapping
    public CompanyResponse saveCompany(@RequestBody CompanyRequest request) {
        return companyServices.save(request);
    }

    @PatchMapping("/{id}")
    public CompanyResponse updateCompany(@RequestBody CompanyRequest request,
                                         @PathVariable Long id) {
        return companyServices.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    public CompanyResponse deleteCompany(@PathVariable("id") Long id) {
        return companyServices.deleteCompany(id);
    }

    @GetMapping
    public CompanyResponseView pagination(@RequestParam(name = "text", required = false) String text,
                                          @RequestParam int size,
                                          @RequestParam int page) {
        return companyServices.pagination(text, size, page);
    }

    @GetMapping("/all")
    public List<CompanyResponse> findAll() {
        return companyServices.getAllCompanies();
    }
}
