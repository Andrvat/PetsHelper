package com.example.petshelper.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String text;
    private String date;
    private Long parentId;
    private String authorUserName;
    private Long userId;

    public CommentDto(Long id, String text, String date, String authorUserName, Long userId, Long parentId) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.authorUserName = authorUserName;
        this.userId = userId;
        this.parentId = parentId;
    }
}
