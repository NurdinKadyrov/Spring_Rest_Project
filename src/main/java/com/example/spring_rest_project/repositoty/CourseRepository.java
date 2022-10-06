package com.example.spring_rest_project.repositoty;

import com.example.spring_rest_project.entities.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.company.id =:id")
    List<Course> findCourseByCompanyId(Long id);

    @Query("select c from Course c where upper(c.courseName) like concat('%',:text,'%') ")
    List<Course> searchCourseByName(String text, Pageable pageable);
}
