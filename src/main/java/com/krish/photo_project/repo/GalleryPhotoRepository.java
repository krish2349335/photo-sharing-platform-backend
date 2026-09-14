package com.krish.photo_project.repo;

import com.krish.photo_project.entity.GalleryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryPhotoRepository extends JpaRepository<GalleryPhoto, Long> {

    List<GalleryPhoto> findByGalleryId(Long galleryId);

    boolean existsByGalleryIdAndPhotoId(Long galleryId, Long photoId);
}
