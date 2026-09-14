package com.krish.photo_project.controller;

import com.krish.photo_project.dto.UserReqDTO;
import com.krish.photo_project.dto.UserResponseDTO;
import com.krish.photo_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getall(){
        return ResponseEntity.ok(userService.getall());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getbyid(@PathVariable("id") long id){
        return ResponseEntity.ok(userService.getbyid(id));
    }

    @PostMapping("/u")
    public ResponseEntity<UserResponseDTO> add(@Valid @RequestBody UserReqDTO dto){
        return new ResponseEntity<>(userService.add(dto), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable("id") long id , @Valid @RequestBody UserReqDTO dto){
        return ResponseEntity.ok(userService.updateUser(id ,dto));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
