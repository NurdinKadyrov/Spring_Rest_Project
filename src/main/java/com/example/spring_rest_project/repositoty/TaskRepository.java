package com.example.spring_rest_project.repositoty;

import com.example.spring_rest_project.entities.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.id =:id")
    Task findTaskById(Long id);

    @Query("select t from Task t where upper(t.taskName) like concat('%',:text,'%') ")
    List<Task> searchByName(String text, Pageable pageable);

}