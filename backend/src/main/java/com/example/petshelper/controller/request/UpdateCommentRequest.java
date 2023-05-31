package com.example.petshelper.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentRequest {
    private Long id;
    private String text;
}
