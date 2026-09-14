package com.krish.photo_project.service;

import com.krish.photo_project.entity.Event;
import com.krish.photo_project.entity.Photo;
import com.krish.photo_project.entity.User;
import com.krish.photo_project.repo.EventRepository;
import com.krish.photo_project.repo.PhotoRepository;
import com.krish.photo_project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepo userRepo;

    public Photo uploadPhoto(MultipartFile file, Long eventId) throws IOException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        String fileName = file.getOriginalFilename();
        Long fileSize = file.getSize();

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!event.getTeamMembers().contains(user)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not assigned to this event"
            );
        }
        Photo photo = new Photo();
        photo.setUploadedBy(user);
        photo.setEvent(event);
        photo.setFileName(fileName);
        photo.setFileSize(fileSize);
        photo.setCreatedAt(LocalDateTime.now());
        String storageUrl = cloudinaryService.upload(file);

        photo.setStorageUrl(storageUrl);

        return photoRepository.save(photo);

    }

    public List<Photo> getPhotosByEvent(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == com.krish.photo_project.entity.Role.ADMIN) {
            return photoRepository.findByEventId(eventId);
        }

        if (!event.getTeamMembers().contains(user)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not assigned to this event"
            );
        }

        return photoRepository.findByEventId(eventId);
    }

    public Photo selectPhoto(Long photoId) {

        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        photo.setSelected(true);

        return photoRepository.save(photo);
    }

    public Photo getPhotoById(Long photoId) {

        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == com.krish.photo_project.entity.Role.ADMIN) {
            return photo;
        }

        if (!photo.getEvent().getTeamMembers().contains(user)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not assigned to this event"
            );
        }

        return photo;
    }

    public List<Photo> getPhotosByEventAndUser(Long eventId, Long userId) {
        return photoRepository.findByEventIdAndUploadedById(eventId, userId);
    }

    public List<Photo> getMyPhotosByEvent(Long eventId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Event not found"
                ));

        if (!event.getTeamMembers().contains(user)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not assigned to this event"
            );
        }

        return photoRepository.findByEventIdAndUploadedById(
                eventId,
                user.getId()
        );
    }
}
