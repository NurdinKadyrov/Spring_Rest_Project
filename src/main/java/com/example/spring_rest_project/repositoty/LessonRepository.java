package com.example.spring_rest_project.repositoty;

import com.example.spring_rest_project.entities.Lesson;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select l from Lesson l where upper(l.lessonName) like concat('%',:text,'%') ")
    public List<Lesson> searchByLessonName(String text, Pageable pageable);
}