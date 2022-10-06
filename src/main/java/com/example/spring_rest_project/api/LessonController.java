package com.example.spring_rest_project.api;

import com.example.spring_rest_project.dto.request.LessonRequest;
import com.example.spring_rest_project.dto.response.LessonResponse;
import com.example.spring_rest_project.dto.responseView.LessonResponseView;
import com.example.spring_rest_project.service.LessonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/lesson")
@PreAuthorize("hasAuthority('INSTRUCTOR')")
public class LessonController {
    private final LessonService lessonService;

    public LessonController(LessonService lessonRepository) {
        this.lessonService = lessonRepository;
    }


    @PostMapping
    public LessonResponse saveLesson(@RequestBody LessonRequest request){
        return lessonService.addLesson(request);
    }

    @GetMapping("{id}")
    public LessonResponse getById(@PathVariable Long id){
        return lessonService.getById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('STUDENT','INSTRUCTOR')")
    public List<LessonResponse> getAll(){
        return lessonService.getAll();
    }


    @PutMapping("/update/{id}")
    public LessonResponse updateLesson(@PathVariable Long id , @RequestBody LessonRequest request){
        return lessonService.updateLesson(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public LessonResponse delete(@PathVariable("id") Long id){
        return lessonService.deleteLesson(id);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    public LessonResponseView pagination(@RequestParam (name = "text") String text,
                                         @RequestParam int page,
                                         @RequestParam int size){
        return lessonService.pagination(text, page, size);
    }
}
