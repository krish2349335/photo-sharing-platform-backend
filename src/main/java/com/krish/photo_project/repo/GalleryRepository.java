package com.krish.photo_project.repo;

import com.krish.photo_project.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    Optional<Gallery> findByShareToken(String shareToken);

    Optional<Gallery> findByEventId(Long eventId);
}
