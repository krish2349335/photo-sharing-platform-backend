package com.krish.photo_project.service;

import com.krish.photo_project.dto.PublicGalleryDTO;
import com.krish.photo_project.entity.Event;
import com.krish.photo_project.entity.Gallery;
import com.krish.photo_project.entity.GalleryPhoto;
import com.krish.photo_project.entity.Photo;
import com.krish.photo_project.repo.EventRepository;
import com.krish.photo_project.repo.GalleryPhotoRepository;
import com.krish.photo_project.repo.GalleryRepository;
import com.krish.photo_project.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GalleryPhotoRepository galleryPhotoRepository;


    @Autowired
    private PhotoRepository photoRepository;

    public Gallery createGallery(Long eventId, String title, String pin) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Event not found"
                ));

        if (galleryRepository.findByEventId(eventId).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Gallery already exists for this event"
            );
        }

        Gallery gallery = new Gallery();

        gallery.setTitle(title);
        gallery.setPin(pin);
        gallery.setEvent(event);
        gallery.setCreatedAt(LocalDateTime.now());
        gallery.setShareToken(UUID.randomUUID().toString());
        gallery.setPublished(false);

        return galleryRepository.save(gallery);
    }


    public GalleryPhoto addPhotoToGallery(Long galleryId, Long photoId) {

        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gallery not found"
                ));

        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Photo not found"
                ));

        if (!photo.getEvent().getId().equals(gallery.getEvent().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Photo not found"
            );
        }

        if (!photo.isSelected()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Photo is not selected"
            );
        }

        if (galleryPhotoRepository.existsByGalleryIdAndPhotoId(
                galleryId,
                photoId
        )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Photo already added to gallery"
            );
        }

        GalleryPhoto galleryPhoto = new GalleryPhoto();

        galleryPhoto.setGallery(gallery);
        galleryPhoto.setPhoto(photo);

        return galleryPhotoRepository.save(galleryPhoto);
    }


    public List<GalleryPhoto> getGalleryPhotos(Long galleryId) {

        return galleryPhotoRepository.findByGalleryId(galleryId);
    }

    public Gallery publishGallery(Long galleryId) {

        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gallery not found"
                ));

        gallery.setPublished(true);

        return galleryRepository.save(gallery);
    }

    public PublicGalleryDTO getGalleryByShareToken(String shareToken) {

        Gallery gallery = galleryRepository
                .findByShareToken(shareToken)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gallery not found"
                ));

        if (!gallery.isPublished()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Gallery not published"
            );
        }

        return new PublicGalleryDTO(
                gallery.getId(),
                gallery.getTitle(),
                gallery.getShareToken(),
                gallery.isPublished()
        );
    }
    public List<Photo> verifyPin(String shareToken, String pin) {

        Gallery gallery = galleryRepository.findByShareToken(shareToken)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gallery not found"
                ));

        if (!gallery.isPublished()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Gallery is not published"
            );
        }

        if (!gallery.getPin().equals(pin)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid PIN"
            );
        }

        return galleryPhotoRepository.findByGalleryId(gallery.getId())
                .stream()
                .map(GalleryPhoto::getPhoto)
                .toList();
    }

    public Gallery getGalleryByEventId(Long eventId) {

        return galleryRepository.findByEventId(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gallery not found"
                ));
    }


}
