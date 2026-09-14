package com.krish.photo_project.service;

import com.krish.photo_project.entity.Event;
import com.krish.photo_project.entity.Role;
import com.krish.photo_project.entity.User;
import com.krish.photo_project.repo.EventRepository;
import com.krish.photo_project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private  EventRepository eventRepository;

    @Autowired
    private UserRepo userRepo;


    public Event createEvent(Event event) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        event.setCreatedBy(user);
        event.setCreatedAt(LocalDateTime.now());

        return eventRepository.save(event);
    }

    public Event assignTeamMember(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Event not found"
                ));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
        event.getTeamMembers().add(user);
        return eventRepository.save(event);

    }

    public List<Event> getAllEvents() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        if (user.getRole() == Role.ADMIN) {
            return eventRepository.findAll();
        }

        return eventRepository.findByTeamMembers_Id(user.getId());
    }



    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }


}
