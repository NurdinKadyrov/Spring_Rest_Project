package com.example.spring_rest_project.api;

import com.example.spring_rest_project.dto.request.TaskRequest;
import com.example.spring_rest_project.dto.response.TaskResponse;
import com.example.spring_rest_project.dto.responseView.TaskResponseView;
import com.example.spring_rest_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/task")
@PreAuthorize("hasAuthority('INSTRUCTOR')")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskRepository) {
        this.taskService = taskRepository;

    }

    @PostMapping
    public TaskResponse save(@RequestBody TaskRequest request){
        return taskService.addTask(request);
    }

    @GetMapping("{id}")
    public TaskResponse getById(@PathVariable Long id){
        return taskService.getById(id);
    }


    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id ,
                                   @RequestBody TaskRequest request){
        return taskService.updateTask(id,request);
    }


    @DeleteMapping("/{id}")
    public TaskResponse delete(@PathVariable("id") Long id){
        return taskService.deleteTask(id);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasAnyAuthority('STUDENT','INSTRUCTOR')")
    public TaskResponseView pagination(@RequestParam(name = "text",required = false) String text,
                                       @RequestParam int page,
                                       @RequestParam int size){
        return taskService.pagination(text, page, size);
    }
}

