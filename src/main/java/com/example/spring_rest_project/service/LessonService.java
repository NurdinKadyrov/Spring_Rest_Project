package com.example.spring_rest_project.service;

import com.example.spring_rest_project.dto.request.LessonRequest;
import com.example.spring_rest_project.dto.response.LessonResponse;
import com.example.spring_rest_project.dto.responseView.LessonResponseView;
import com.example.spring_rest_project.entities.Course;
import com.example.spring_rest_project.entities.Lesson;
import com.example.spring_rest_project.exeptions.NotFoundException;
import com.example.spring_rest_project.repositoty.CourseRepository;
import com.example.spring_rest_project.repositoty.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonResponse addLesson(LessonRequest request) {
        Lesson lesson = mapToEntity(request);
        return mapToResponse(lessonRepository.save(lesson));
    }

    private LessonResponse mapToResponse(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .lessonName(lesson.getLessonName())
                .build();
    }

    private Lesson mapToEntity(LessonRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("course with id -%s not found!", request.getCourseId())));
        return lessonRepository.save(Lesson.builder()
                .lessonName(request.getLessonName())
                .course(course).build());
    }

    public LessonResponse getById(Long id) {
        return mapToResponse(lessonRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("lesson with id -%s not found", id))));
    }

    public List<LessonResponse> getAll() {
        List<LessonResponse> responses = new ArrayList<>();
        for (Lesson lesson : lessonRepository.findAll()) {
            responses.add(mapToResponse(lesson));
        }
        return responses;
    }

    public LessonResponse updateLesson(Long id, LessonRequest request){
        Lesson lesson1 =lessonRepository.findById(id).orElseThrow(
        () -> new NotFoundException(String.format("lesson with id - %s not found!",id)));

        Lesson lesson = update(lesson1,request);
             return mapToResponse(lesson);
    }

    public Lesson update(Lesson lesson, LessonRequest request) {
         lesson.setLessonName(request.getLessonName());
        return lessonRepository.save(lesson);
    }

    public LessonResponse deleteLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("lesson with id -%s not found ", id)));
        lesson.setCourse(null);
        lessonRepository.delete(lesson);
        return mapToResponse(lesson);
    }

    public List<Lesson> search(String lessonName, Pageable pageable) {
        String text = lessonName == null ? "" : lessonName;
        return lessonRepository.searchByLessonName(text.toUpperCase(), pageable);
    }

    public List<LessonResponse> getAll(List<Lesson> lessons) {
        List<LessonResponse> responses = new ArrayList<>();
        for (Lesson lesson : lessons) {
            responses.add(mapToResponse(lesson));
        }
        return responses;
    }

    public LessonResponseView pagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        LessonResponseView lessonResponseView = new LessonResponseView();
        lessonResponseView.setResponseList(getAll(search(text, pageable)));
        return lessonResponseView;
    }
}