package com.example.petshelper.exceptions;

public class PostDescriptionAlreadyExits extends Exception{
    public PostDescriptionAlreadyExits() {
        super("Post with such description already exists");
    }
}
