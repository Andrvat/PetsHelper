package com.example.petshelper.controller;

import com.example.petshelper.RequestResult;
import com.example.petshelper.TestsMockProvider;
import com.example.petshelper.controller.dto.PostDto;
import com.example.petshelper.model.Role;
import com.example.petshelper.model.Tag;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.service.PersonalityService;
import com.example.petshelper.service.TagService;
import com.example.petshelper.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @MockBean
    private PersonalityService personalityService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserService userService;

    private final Gson gson = new Gson();

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void testGetTagByPart() throws Exception {
        String part = "кот";
        List<Tag> matches = new ArrayList<>() {{
            add(new Tag("кот"));
            add(new Tag("котята"));
            add(new Tag("котенок"));
        }};
        when(tagService.getTagByPart(part)).thenReturn(matches);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/tag/getTagByPart/" + part, null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        Tag[] tags = this.gson.fromJson(actualResponse.getContent(), Tag[].class);
        Assertions.assertEquals(matches.size(), tags.length);
    }

    @Test
    public void testGetTagByPartEmpty() throws Exception {
        String part = "кот";
        when(tagService.getTagByPart(part)).thenReturn(new ArrayList<>());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/tag/getTagByPart/" + part, null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.getStatus());
    }

    @Test
    public void testCreateTag() throws Exception {
        String body = "кот";
        Long id = 1L;
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        when(tagService.createTag(body)).thenReturn(id);
        RequestResult actualResponse = new RequestResult(
                mockMvc.perform(post("/tag/create")
                        .content(body)).andReturn().getResponse());
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        Assertions.assertEquals(String.valueOf(id), actualResponse.getContent());
    }

}