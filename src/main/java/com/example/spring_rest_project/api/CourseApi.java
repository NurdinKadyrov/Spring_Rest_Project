package com.example.spring_rest_project.api;


import com.example.spring_rest_project.dto.request.CourseRequest;
import com.example.spring_rest_project.dto.response.CourseResponse;
import com.example.spring_rest_project.dto.responseView.CourseResponseView;
import com.example.spring_rest_project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/course")
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseApi {
    private final CourseService courseService;

    @Autowired
    public CourseApi(@Qualifier("courseService")CourseService courseRepository) {
        this.courseService = courseRepository;
    }

    @PostMapping
    public CourseResponse save(@RequestBody CourseRequest request){
        return courseService.save(request);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
    public CourseResponse getById(@PathVariable Long id){
        return courseService.getById(id);
    }

    @GetMapping("all/{id}")
    public List<CourseResponse> getCoursesByCompanyId(@PathVariable Long id){
        return courseService.getCourseByCompanyId(id);
    }

    @PatchMapping("/update/{id}")
    public CourseResponse updateCourse(@PathVariable Long id , @RequestBody CourseRequest request){
        return courseService.updateByCourseId(id, request);
    }

    @DeleteMapping("{id}")
    public CourseResponse delete(@PathVariable Long id){
        return courseService.deleteCourse(id);
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public CourseResponseView pagination(@RequestParam(name = "text",required = false) String text,
                                         @RequestParam int page,
                                         @RequestParam int size) {
        return courseService.pagination(text, page, size);
    }
}
