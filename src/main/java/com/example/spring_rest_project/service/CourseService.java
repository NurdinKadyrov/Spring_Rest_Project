package com.example.spring_rest_project.service;

import com.example.spring_rest_project.dto.request.CourseRequest;
import com.example.spring_rest_project.dto.response.CourseResponse;
import com.example.spring_rest_project.dto.responseView.CourseResponseView;
import com.example.spring_rest_project.entities.Company;
import com.example.spring_rest_project.entities.Course;
import com.example.spring_rest_project.entities.Instructor;
import com.example.spring_rest_project.exeptions.NotFoundException;
import com.example.spring_rest_project.repositoty.CompanyRepository;
import com.example.spring_rest_project.repositoty.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository, CompanyRepository companyRepository) {
        this.courseRepository = courseRepository;
        this.companyRepository = companyRepository;
    }

    public CourseResponse save(CourseRequest request) {
        Course course = mapToEntity(request);
        return mapToResponse(courseRepository.save(course));
    }

    public Course mapToEntity(CourseRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("company with id - %s not found!", request.getCompanyId())));
        Course course = Course.builder()
                .courseName(request.getCourseName())
                .dateOfStart(request.getDateOfStart())
                .duration(request.getDuration())
                .description(request.getDescription())
                .company(company).build();
        courseRepository.save(course);
        return course;
    }

    public CourseResponse mapToResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .dateOfStart(course.getDateOfStart())
                .duration(course.getDuration())
                .build();
    }

    public List<CourseResponse> getCourseByCompanyId(Long id) {
        List<CourseResponse> responses = new ArrayList<>();
        for (Course c : courseRepository.findCourseByCompanyId(id)) {
            responses.add(mapToResponse(c));
        }
        return responses;
    }

    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("course with id - %s not found!", id)));
        return mapToResponse(course);
    }

    public CourseResponse updateByCourseId(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("course with id - %s not found!", id)));
        return mapToResponse(courseRepository.save(course));
    }

    public Course update(Course course, CourseRequest request) {
        String courseName = course.getCourseName();
        String description = course.getDescription();
        String duration = course.getDuration();
        LocalDate dateOfStart = course.getDateOfStart();
        String newCourseName = request.getCourseName();
        String newDescription = request.getDescription();
        String newDuration = request.getDuration();
        LocalDate newDateOfStart = request.getDateOfStart();
        if (newCourseName != null && !newCourseName.equals(courseName)) {
            course.setCourseName(newCourseName);
        }
        if (newDescription != null && !newDescription.equals(description)) {
            course.setDescription(newDescription);
        }
        if (newDuration != null && !newDuration.equals(duration)) {
            course.setDuration(newDuration);
        }
        if (newDateOfStart != null && !newDateOfStart.equals(dateOfStart)) {
            course.setDateOfStart(newDateOfStart);
        }
        return courseRepository.save(course);
    }

    public CourseResponse deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("course with id - %s not found", id)));
        for (Instructor instructor : course.getInstructors()) {
            instructor.setCourses(null);
        }
        course.setCompany(null);
        courseRepository.delete(course);
        return mapToResponse(course);
    }

    public List<Course> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return courseRepository.searchCourseByName(text, pageable);
    }

    public List<CourseResponse> getAll(List<Course> courses) {
        List<CourseResponse> responses = new ArrayList<>();
        for (Course course : courses) {
            responses.add(mapToResponse(course));
        }
        return responses;
    }

    public CourseResponseView pagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        CourseResponseView courseResponseView = new CourseResponseView();
        courseResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        courseResponseView.setTotalPage(courseRepository.findAll(pageable).getTotalPages());
        courseResponseView.setResponses(getAll(search(text, pageable)));
        return courseResponseView;
//        Pageable pageable1 = PageRequest.of(page-1,size);
//        Page<Course> coursePage = courseRepository.findAll(pageable1);
//        return CourseResponseView.builder()
//                .currentPage(pageable1.getPageNumber()+1)
//                .totalPage(coursePage.getTotalPages())
//                .responses(getAll(search(text,pageable1)))
//                .build();
    }
}