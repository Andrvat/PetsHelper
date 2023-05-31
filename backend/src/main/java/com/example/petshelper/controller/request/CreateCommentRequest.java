package com.example.petshelper.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequest {
    private Long postId;
    private String text;
    private Long parentId;
}
