package com.example.spring_rest_project.api;

import com.example.spring_rest_project.dto.request.AssignInstructorRequest;
import com.example.spring_rest_project.dto.request.InstructorRequest;
import com.example.spring_rest_project.dto.response.InstructorResponse;
import com.example.spring_rest_project.dto.responseView.InstructorResponseView;
import com.example.spring_rest_project.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/instructor")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorRepository) {
        this.instructorService = instructorRepository;
    }

    @PostMapping
    public InstructorResponse saveIns(@RequestBody InstructorRequest request){
        return instructorService.save(request);
    }

    @GetMapping("{id}")
    public InstructorResponse getById(@PathVariable Long id){
        return instructorService.getById(id);
    }

    @GetMapping("/allCompanyId/{id}")
    public List<InstructorResponse> findInstructorByCompanyId(@PathVariable Long id){
        return instructorService.getInstructorByCompanyId(id);
    }

    @GetMapping("/cou/{id}")
    public List<InstructorResponse> findByCourseId(@PathVariable Long id){
        return instructorService.getInstructorsByCourseId(id);
    }

    @PutMapping("{id}")
    public InstructorResponse updateIns(@PathVariable Long id,
                                        @RequestBody InstructorRequest request){
        return instructorService.updateById(id,request);
    }

    @DeleteMapping("/{id}")
    public InstructorResponse delete(@PathVariable("id") Long id){
        return instructorService.deleteInstructor(id);
    }

    @PostMapping("/assign")
    public String assign(@RequestBody AssignInstructorRequest request){
         return instructorService.assignInstructorToCourse(request);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public InstructorResponseView pagination(@RequestParam(name = "text",required = false) String text,
                                             @RequestParam int page,
                                             @RequestParam int size){
        return instructorService.pagination(text, page, size);
    }

}
