package com.example.petshelper.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class PostResponse {
    private boolean ok;
    private String error;
}
