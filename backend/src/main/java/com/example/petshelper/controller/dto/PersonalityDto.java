package com.example.petshelper.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalityDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String phoneNumber;
    private String gender;
    private String aboutMe;
    private Long postsNumber;
    private Long subscribersNumber;
}
