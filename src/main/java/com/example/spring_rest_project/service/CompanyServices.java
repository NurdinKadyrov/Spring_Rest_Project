package com.example.spring_rest_project.service;

import com.example.spring_rest_project.dto.request.CompanyRequest;
import com.example.spring_rest_project.dto.response.CompanyResponse;
import com.example.spring_rest_project.dto.responseView.CompanyResponseView;
import com.example.spring_rest_project.entities.Company;
import com.example.spring_rest_project.exeptions.NotFoundException;
import com.example.spring_rest_project.repositoty.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServices {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServices(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyResponse save(CompanyRequest companyRequest) {
        Company company = mapToEntity(companyRequest);
        return mapToResponse(companyRepository.save(company));
    }

    private CompanyResponse mapToResponse(Company company) {
        return CompanyResponse.builder().id(company.getId())
                .companyName(company.getCompanyName())
                .locatedCountry(company.getLocatedCountry())
                .build();
    }

    public Company mapToEntity(CompanyRequest companyRequest) {
        return Company.builder().companyName(companyRequest.getCompanyName())
                .locatedCountry(companyRequest.getLocatedCountry())
                .build();
    }

    public List<CompanyResponse> getAllCompanies() {
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company company : companyRepository.findAll()) {
            responses.add(mapToResponse(company));
        }
        return responses;
    }

    public CompanyResponse getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("company with id - %s not found!", id)));
        return mapToResponse(company);
    }

    public Company update(Company company, CompanyRequest companyRequest) {
        company.setCompanyName(company.getCompanyName());
        company.setLocatedCountry(company.getLocatedCountry());
        companyRepository.save(company);
        return company;
    }

    public CompanyResponse updateById(Long id, CompanyRequest request) {
        Company company = companyRepository.findById(id).get();
        String companyName = company.getCompanyName();
        String newCompanyName = request.getCompanyName();
        String locatedCountry = company.getLocatedCountry();
        String newLocatedCountry = request.getLocatedCountry();
        if (newCompanyName != null && !newCompanyName.equals(companyName)) {
            company.setCompanyName(newCompanyName);
        }
        if (newLocatedCountry != null && !newLocatedCountry.equals(locatedCountry)) {
            company.setLocatedCountry(newLocatedCountry);
        }
        Company company1 = update(company, request);
        return mapToResponse(companyRepository.save(company1));
    }

    public CompanyResponse deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("company with id - %s not found!", id)));
        companyRepository.delete(company);
        return mapToResponse(company);
    }

    public List<Company> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return companyRepository.searchByCompanyName(text.toUpperCase(), pageable);
    }

    public List<CompanyResponse> getAll(List<Company> companies) {
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company c : companies) {
            responses.add(mapToResponse(c));
        }
        return responses;
    }

    public CompanyResponseView pagination(String text, int size, int page) {
        CompanyResponseView companyResponseView = new CompanyResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Company> companies = companyRepository.findAll(pageable);
        companyResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        companyResponseView.setTotalPage(companies.getTotalPages());
        companyResponseView.setCompanyResponseList(getAll(search(text, pageable)));
        return companyResponseView;
    }
}
