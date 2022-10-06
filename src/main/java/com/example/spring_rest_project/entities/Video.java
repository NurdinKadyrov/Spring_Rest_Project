package com.example.spring_rest_project.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(generator = "video_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "video_gen", sequenceName = "video_seq", allocationSize = 1)
    private Long id;
    @Column(name = "video_name")
    private String videoName;
    private String link;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public Video(String videoName, String link) {
        this.videoName = videoName;
        this.link = link;
    }
}
