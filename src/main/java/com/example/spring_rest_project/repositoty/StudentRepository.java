package com.example.spring_rest_project.repositoty;

import com.example.spring_rest_project.entities.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s where upper(s.firstName) like concat('%',:text,'%') " +
            "or upper(s.lastName) like concat('%',:text,'%') ")
    List<Student> searchByEmail(String text, Pageable pageable);

    @Query("select case when count(a)>0 then true else false end " +
            "from User a where a.email = ?1")
    boolean existsByUserEmail(String email);
}