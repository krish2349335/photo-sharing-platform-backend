package com.krish.photo_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gallery_photos")
@Getter
@Setter
public class GalleryPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;
}
