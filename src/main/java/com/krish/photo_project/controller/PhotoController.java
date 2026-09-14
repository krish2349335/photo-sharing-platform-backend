package com.krish.photo_project.controller;

import com.krish.photo_project.entity.Photo;
import com.krish.photo_project.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    public ResponseEntity<Photo> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("eventId") Long eventId) throws Exception {

        Photo photo = photoService.uploadPhoto(file, eventId);

        return ResponseEntity.ok(photo);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Photo>> getPhotosByEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                photoService.getPhotosByEvent(eventId)
        );
    }

    @PutMapping("/{photoId}/select")
    public ResponseEntity<Photo> selectPhoto(
            @PathVariable Long photoId) {

        return ResponseEntity.ok(
                photoService.selectPhoto(photoId)
        );
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<Photo> getPhotoById(
            @PathVariable Long photoId) {

        return ResponseEntity.ok(
                photoService.getPhotoById(photoId)
        );
    }

    @GetMapping("/event/{eventId}/user/{userId}")
    public ResponseEntity<List<Photo>> getPhotosByEventAndUser(
            @PathVariable Long eventId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                photoService.getPhotosByEventAndUser(eventId, userId)
        );
    }

    @GetMapping("/my/event/{eventId}")
    public ResponseEntity<List<Photo>> getMyPhotosByEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                photoService.getMyPhotosByEvent(eventId)
        );
    }
}
