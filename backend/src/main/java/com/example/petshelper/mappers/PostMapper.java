package com.example.petshelper.mappers;

import com.example.petshelper.controller.dto.PostDto;
import com.example.petshelper.model.Post;
import com.example.petshelper.model.Tag;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.repository.PersonalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {
    public static PostDto toDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());

        UserPersonality personality = post.getAuthor();
        postDto.setAuthorNumber(personality.getPhoneNumber());
        postDto.setAuthorUsername(personality.getCredential().getUsername());
        postDto.setAuthorEmail(personality.getCredential().getEmail());

        postDto.setPostHeader(post.getHeader());
        postDto.setCity(post.getCity());

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");

        postDto.setDate(format.format(post.getDate()));

        postDto.setDescription(post.getDescription());
        if(post.getCategory() != null) {
            postDto.setCategoryName(post.getCategory().getRussianName());
            postDto.setSectionName(post.getCategory().getSection().getName());
        }
        String actuality = post.getIsActual() ? "Открыто" : "В архиве";
        postDto.setViewsNumber(11);
        postDto.setActuality(actuality);

        List<Tag> tags = post.getTags();
        StringBuilder stringOfTags = new StringBuilder();
        for(Tag tag: tags) {
            stringOfTags.append(tag.getName());
        }
        postDto.setTags(stringOfTags.toString());
        postDto.setImages(post.getImages().stream().map(i -> new String(i.getImageData())).collect(Collectors.toList()));
        return postDto;
    }

    public static List<PostDto> toDto(List<Post> posts) {
        List<PostDto> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(PostMapper.toDto(post));
        }
        return postDTOs;
    }
}
