package com.example.spring_rest_project.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(generator = "lesson_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "lesson_gen", sequenceName = "lesson_seq", allocationSize = 1)
    private Long id;
    @Column(name = "lesson_name")
    private String lessonName;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lesson")
    private List<Task> tasks;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lesson")
    private Video video;

    public Lesson(String lessonName) {
        this.lessonName = lessonName;
    }

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }
}
