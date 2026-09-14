package com.krish.photo_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "galleries")
@Getter
@Setter
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String shareToken;

    private String pin;

    private boolean published = false;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
