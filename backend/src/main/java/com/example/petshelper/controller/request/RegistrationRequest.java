package com.example.petshelper.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    private String username;
    private String email;
    private String password;
    private String city;
    private String role;
}