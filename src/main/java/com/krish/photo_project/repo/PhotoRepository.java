package com.krish.photo_project.repo;

import com.krish.photo_project.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByEventId(Long eventId);

    Optional<Photo> findById(Long id);

    List<Photo> findByEventIdAndUploadedById(Long eventId, Long userId);
}
