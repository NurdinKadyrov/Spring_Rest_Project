package com.example.spring_rest_project.entities;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(generator = "company_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "company_gen", sequenceName = "company_seq", allocationSize = 1)
    private Long id;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "located_country")
    private String locatedCountry;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Course> courses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Instructor> instructors;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
    private List<Student> students;

    public Company(String companyName, String locatedCountry) {
        this.companyName = companyName;
        this.locatedCountry = locatedCountry;
    }

    public void addCourse(Course course){
        if(courses == null){
            courses = new ArrayList<>();
        }
        courses.add(course);
    }

    public void addInstructor(Instructor instructor){
        if(instructors == null){
             instructors = new ArrayList<>();
        }
        instructors.add(instructor);
    }

    public void addStudent(Student student){
        if(students == null){
            students = new ArrayList<>();
        }
        students.add(student);
    }
}
