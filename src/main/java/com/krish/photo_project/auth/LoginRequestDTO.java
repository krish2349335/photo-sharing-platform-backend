package com.krish.photo_project.auth;


import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}
