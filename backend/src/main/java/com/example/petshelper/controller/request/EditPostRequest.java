package com.example.petshelper.controller.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditPostRequest {
    private String postHeader;
    private String description;
    private String categoryName;
    private String city;
    private String tags;
}
