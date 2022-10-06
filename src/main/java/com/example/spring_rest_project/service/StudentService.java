package com.example.spring_rest_project.service;

import com.example.spring_rest_project.dto.request.AssignStudentRequest;
import com.example.spring_rest_project.dto.request.StudentRequest;
import com.example.spring_rest_project.dto.response.StudentResponse;
import com.example.spring_rest_project.dto.responseView.StudentResponseView;
import com.example.spring_rest_project.entities.Course;
import com.example.spring_rest_project.entities.Student;
import com.example.spring_rest_project.entities.User;
import com.example.spring_rest_project.entities.enums.Role;
import com.example.spring_rest_project.exeptions.BadRequestException;
import com.example.spring_rest_project.exeptions.NotFoundException;
import com.example.spring_rest_project.repositoty.CompanyRepository;
import com.example.spring_rest_project.repositoty.CourseRepository;
import com.example.spring_rest_project.repositoty.StudentRepository;
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
public class StudentService {

    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentResponse addStudents(StudentRequest request) {
        return mapToResponse(studentRepository.save(mapToEntity(request)));
    }

    public Student mapToEntity(StudentRequest request) {
        String email = request.getEmail();
        if (studentRepository.existsByUserEmail(email)) {
            throw new BadRequestException("This email is already taken!");
        }
        return studentRepository.save(Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .studyFormat(request.getStudyFormat())
                .user(User.builder()
                        .password(passwordEncoder.encode(request.getPassword()))
                        .createdDate(LocalDate.now())
                        .role(Role.STUDENT)
                        .email(email)
                        .build())
                .company(companyRepository.findById(request.getCompanyId()).orElseThrow(
                        () -> new NotFoundException(
                                String.format("company with id - %s not found!", request.getCompanyId()))))
                .build());
    }

    public StudentResponse mapToResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .email(student.getUser().getEmail())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .phoneNumber(student.getPhoneNumber())
                .studyFormat(student.getStudyFormat())
                .build();
    }

    public StudentResponse getById(Long id) {
        return mapToResponse(studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("student with id - %s not found!", id))));
    }

    public List<StudentResponse> getAll() {
        List<StudentResponse> responses = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            responses.add(mapToResponse(student));
        }
        return responses;
    }

    public Student updateStudent(Student student, StudentRequest request) {
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setStudyFormat(request.getStudyFormat());
        student.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
        student.getUser().setRole(Role.STUDENT);
        student.getUser().setEmail(request.getEmail());
        return student;
    }

    public StudentResponse updateById(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("student with id %s not found", id)));
        Student student1 = updateStudent(student, request);
        return mapToResponse(studentRepository.save(student1));
    }

    public StudentResponse deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("student with id - %s not found!", id))
        );
        student.setCourse(null);
        studentRepository.delete(student);
        return mapToResponse(student);
    }

    public String assignStudentToCourse(AssignStudentRequest request) {
        Student student = studentRepository.findById(request.getStudentId()).orElseThrow(
                () -> new NotFoundException(String.format("student with id - %s not found!", request.getStudentId()))
        );
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(
                () -> new NotFoundException(String.format("course with id %s not found!", request.getCourseId()))
        );
        student.setCourse(course);
        course.addStudent(student);
        studentRepository.save(student);
        return String.format(
                "student with id %s successful assigned with courseId %s",
                request.getStudentId(), request.getCourseId());
    }

    public List<Student> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return studentRepository.searchByEmail(text, pageable);
    }

    public List<StudentResponse> getAll(List<Student> students) {
        List<StudentResponse> responses = new ArrayList<>();
        for (Student s : students) {
            responses.add(mapToResponse(s));
        }
        return responses;
    }

    public StudentResponseView pagination(String text, int size, int page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        StudentResponseView studentResponseView = new StudentResponseView();
        Page<Student> students = studentRepository.findAll(pageable);
        studentResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        studentResponseView.setTotalPage(students.getTotalPages());
        studentResponseView.setResponses(getAll(search(text, pageable)));
        return studentResponseView;
    }
}
