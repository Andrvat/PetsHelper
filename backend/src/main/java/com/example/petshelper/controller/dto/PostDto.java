package com.example.petshelper.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostDto {
    private Long id;

    private String authorUsername;
    private String authorEmail;
    private String authorNumber;

    private String postHeader;
    private String description;
    private String date;
    private String categoryName;
    private String sectionName;
    private String city;
    private String actuality;
    private Integer viewsNumber;
    private String tags;
    private List<String> images;
}
