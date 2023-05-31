package com.example.petshelper.controller.request;

import com.example.petshelper.model.UserPersonality;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdatePersonalityRequest {
    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private String city;
    private String phoneNumber;
    private String email;
    private String aboutMe;
    private String gender;
}
