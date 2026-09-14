package com.krish.photo_project.service;

import com.krish.photo_project.dto.UserReqDTO;
import com.krish.photo_project.dto.UserResponseDTO;
import com.krish.photo_project.entity.User;
import com.krish.photo_project.exception.ResourceNotFoundException;
import com.krish.photo_project.mapper.UserMapper;
import com.krish.photo_project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserResponseDTO add(UserReqDTO dto){
        User user = UserMapper.dtotoEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepo.save(user);
        return UserMapper.toResponsedto(saved);
    }

    public List<UserResponseDTO> getall() {
        return this.userRepo.findAll().stream().map(UserMapper::toResponsedto).toList();
    }

    public UserResponseDTO getbyid(Long id){
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id));
        return UserMapper.toResponsedto(user);
    }


    public UserResponseDTO updateUser(Long id, UserReqDTO dto) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        User updatedUser = userRepo.save(user);

        return UserMapper.toResponsedto(updatedUser);
    }

    public void deleteUser(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        userRepo.delete(user);
    }

}
