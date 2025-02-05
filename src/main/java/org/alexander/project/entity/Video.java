package org.alexander.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "videos")
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String path;

    public Video(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Video() {
    }
}