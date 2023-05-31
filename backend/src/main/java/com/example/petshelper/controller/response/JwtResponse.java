package com.example.petshelper.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class JwtResponse {
    private String jwt;
    private String username;
    private Boolean status;
    private Boolean isAdmin;
}
