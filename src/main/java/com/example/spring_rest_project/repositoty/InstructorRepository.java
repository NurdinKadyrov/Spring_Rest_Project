package com.example.spring_rest_project.repositoty;

import com.example.spring_rest_project.entities.Instructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query("select case when count(a)>0 then true else false end" +
            " from User a where a.email =?1")
    boolean existsByUserEmail(String email);

      @Query("select i from Instructor i join i.courses c where i.id=:id")
    List<Instructor> findInstructorByCoursesId(Long id);

    @Query("select i from Instructor  i where i.company.id=:id")
    List<Instructor> findInstructorByCompanyId(Long id);

    @Query("select i from Instructor i where upper(i.firstName) like concat('%',:text,'%')")
    List<Instructor> searchByEmail(String text, Pageable pageable);
}