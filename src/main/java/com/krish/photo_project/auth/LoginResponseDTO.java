package com.krish.photo_project.auth;

import com.krish.photo_project.entity.Role;
import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;
    private Role role;

    public LoginResponseDTO(String token, Role role) {
        this.token = token;
        this.role = role;
    }
}
