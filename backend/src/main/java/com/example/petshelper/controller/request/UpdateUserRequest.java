package com.example.petshelper.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String token;
    private String password;
}
