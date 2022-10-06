package com.example.spring_rest_project.repositoty;

import com.example.spring_rest_project.entities.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("select v from Video v where v.lesson.id =:id")
    List<Video> findVideoByLessonId(Long id);

    @Query("select v from Video v where v.id =:id")
    Video findVideoById(Long id);

    @Query("select v from Video  v where upper(v.videoName) like  concat('%',:text,'%') ")
    List<Video> searchByVideoName(String text, Pageable pageable);

}