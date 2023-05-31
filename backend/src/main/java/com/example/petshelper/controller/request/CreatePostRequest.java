package com.example.petshelper.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePostRequest {
    private String header;
    private String description;
    private String category;
    private String section;
    private String tags;
    private List<String> images;
}
