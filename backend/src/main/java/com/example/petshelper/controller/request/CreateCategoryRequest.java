package com.example.petshelper.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {
    private String name;
    private Long sectionId;
}
