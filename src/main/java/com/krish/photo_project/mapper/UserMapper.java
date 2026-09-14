package com.krish.photo_project.mapper;

import com.krish.photo_project.dto.UserReqDTO;
import com.krish.photo_project.dto.UserResponseDTO;
import com.krish.photo_project.entity.User;

public class UserMapper {

    public static User dtotoEntity(UserReqDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        user.setPassword(dto.getPassword());
        return user;
    }

    public static  UserResponseDTO toResponsedto(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());

        return responseDTO;
    }
}
