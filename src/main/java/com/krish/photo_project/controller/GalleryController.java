package com.krish.photo_project.controller;

import com.krish.photo_project.dto.PublicGalleryDTO;
import com.krish.photo_project.entity.Gallery;
import com.krish.photo_project.entity.GalleryPhoto;
import com.krish.photo_project.entity.Photo;
import com.krish.photo_project.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/galleries")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @PostMapping
    public ResponseEntity<Gallery> createGallery(
            @RequestParam Long eventId,
            @RequestParam String title,
            @RequestParam String pin) {

        return ResponseEntity.ok(
                galleryService.createGallery(eventId, title, pin)
        );
    }

    @PostMapping("/{galleryId}/photos/{photoId}")
    public ResponseEntity<GalleryPhoto> addPhotoToGallery(
            @PathVariable Long galleryId,
            @PathVariable Long photoId) {

        return ResponseEntity.ok(
                galleryService.addPhotoToGallery(galleryId, photoId)
        );
    }

    @GetMapping("/{galleryId}/photos")
    public ResponseEntity<List<GalleryPhoto>> getGalleryPhotos(
            @PathVariable Long galleryId) {

        return ResponseEntity.ok(
                galleryService.getGalleryPhotos(galleryId)
        );
    }

    @PutMapping("/{galleryId}/publish")
    public ResponseEntity<Gallery> publishGallery(
            @PathVariable Long galleryId) {

        return ResponseEntity.ok(
                galleryService.publishGallery(galleryId)
        );
    }

    @GetMapping("/public/{shareToken}")
    public ResponseEntity<PublicGalleryDTO> getGalleryByShareToken(
            @PathVariable String shareToken) {

        return ResponseEntity.ok(
                galleryService.getGalleryByShareToken(shareToken)
        );
    }

    @PostMapping("/public/{shareToken}/verify")
    public ResponseEntity<List<Photo>> verifyPin(
            @PathVariable String shareToken,
            @RequestParam String pin) {

        return ResponseEntity.ok(
                galleryService.verifyPin(shareToken, pin)
        );
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Gallery> getGalleryByEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                galleryService.getGalleryByEventId(eventId)
        );
    }
}
