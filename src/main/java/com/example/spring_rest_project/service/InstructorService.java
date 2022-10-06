package com.example.spring_rest_project.service;

import com.example.spring_rest_project.dto.request.AssignInstructorRequest;
import com.example.spring_rest_project.dto.request.InstructorRequest;
import com.example.spring_rest_project.dto.response.InstructorResponse;
import com.example.spring_rest_project.dto.responseView.InstructorResponseView;
import com.example.spring_rest_project.entities.Company;
import com.example.spring_rest_project.entities.Course;
import com.example.spring_rest_project.entities.Instructor;
import com.example.spring_rest_project.entities.User;
import com.example.spring_rest_project.entities.enums.Role;
import com.example.spring_rest_project.exeptions.BadRequestException;
import com.example.spring_rest_project.exeptions.NotFoundException;
import com.example.spring_rest_project.repositoty.CompanyRepository;
import com.example.spring_rest_project.repositoty.CourseRepository;
import com.example.spring_rest_project.repositoty.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final InstructorRepository instructorRepository;


    public Instructor mapToEntity(InstructorRequest request) {
        String email = request.getEmail();
        if (instructorRepository.existsByUserEmail(email)) {
            throw new BadRequestException(("this email is already taken!"));
        }
        Company company = companyRepository.findById(request.getCompanyId()).
                orElseThrow(() -> new NotFoundException(String.format(" instructor not found! %s", request.getCompanyId())));
        User user = User.builder()
                .createdDate(LocalDate.now())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.INSTRUCTOR).build();
        Instructor instructor = Instructor.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .specialization(request.getSpecialization())
                .email(request.getEmail())
                .company(company)
                .user(user)
                .build();
        return instructorRepository.save(instructor);
    }

    public InstructorResponse save(InstructorRequest request) {
        Instructor instructor = mapToEntity(request);
        return mapToResponse(instructorRepository.save(instructor));
    }

    private InstructorResponse mapToResponse(Instructor instructor) {
        return InstructorResponse.builder()
                .id(instructor.getId())
                .firstName(instructor.getFirstName())
                .lastName(instructor.getLastName())
                .phoneNumber(instructor.getPhoneNumber())
                .specialization(instructor.getSpecialization())
                .email(instructor.getEmail())
                .build();
    }

    public InstructorResponse getById(Long id) {
        return mapToResponse(instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("instructor not found %s", id))));
    }

    public List<InstructorResponse> getInstructorsByCourseId(Long id) {
        List<InstructorResponse> responses = new ArrayList<>();
        for (Instructor instructor : instructorRepository.findInstructorByCoursesId(id)) {
            responses.add(mapToResponse(instructor));
        }
        return responses;
    }

    public List<InstructorResponse> getInstructorByCompanyId(Long id) {
        List<InstructorResponse> responses = new ArrayList<>();
        for (Instructor instructor : instructorRepository.findInstructorByCompanyId(id)) {
            responses.add(mapToResponse(instructor));
        }
        return responses;
    }

    public Instructor update(Instructor instructor, InstructorRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.INSTRUCTOR).build();
        return Instructor.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .specialization(request.getSpecialization())
                .user(user).build();
    }

    public InstructorResponse updateById(Long id, InstructorRequest request) {
        return mapToResponse(instructorRepository
                .save(update(instructorRepository.findById(id).
                        orElseThrow(() -> new org.webjars
                                .NotFoundException(String.format("not found - %s", id))), request)));
    }

    public InstructorResponse deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("not found - %s", id)));
        instructorRepository.delete(instructor);
        return mapToResponse(instructor);
    }

    public String assignInstructorToCourse(AssignInstructorRequest request) {
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(
                () -> new NotFoundException(String
                        .format("course with id - %s not found", request.getCourseId())));
        Instructor instructor = instructorRepository.findById(request.getInstructorId()).orElseThrow(
                () -> new NotFoundException(String
                        .format("instructor with id - %s not found", request.getInstructorId())));
        course.addInstructor(instructor);
        instructor.addCourse(course);
        instructorRepository.save(instructor);
        return String.format("instructor with id - %s successful assign  course with - %s",
                request.getInstructorId(), request.getCourseId());
    }

    public List<Instructor> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return instructorRepository.searchByEmail(text.toUpperCase(), pageable);
    }

    public List<InstructorResponse> getAll(List<Instructor> instructors) {
        List<InstructorResponse> responses = new ArrayList<>();
        for (Instructor instructor : instructors) {
            responses.add(mapToResponse(instructor));
        }
        return responses;
    }

    public InstructorResponseView pagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Instructor> responseViews = instructorRepository.findAll(pageable);
        return InstructorResponseView.builder()
                .currentPage(pageable.getPageNumber() + 1)
                .totalPage(responseViews.getTotalPages())
                .responses(getAll(search(text, pageable)))
                .build();
    }
}
