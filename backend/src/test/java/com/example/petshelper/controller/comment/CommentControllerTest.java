package com.example.petshelper.controller.comment;

import com.example.petshelper.RequestResult;
import com.example.petshelper.TestsMockProvider;
import com.example.petshelper.controller.request.CreateCommentRequest;
import com.example.petshelper.controller.request.GetCommentsByPostRequest;
import com.example.petshelper.controller.request.UpdateCommentRequest;
import com.example.petshelper.exceptions.NotFoundException;
import com.example.petshelper.model.Role;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.service.CommentService;
import com.example.petshelper.service.PersonalityService;
import com.example.petshelper.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @Autowired
    private CommentController commentController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PersonalityService personalityService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtProvider jwtProvider;

    private final Gson gson = new Gson();

    private final String testCreateCommentRequest = this.gson.toJson(
            new CreateCommentRequest() {{
                setPostId(1L);
                setText("New comment text");
                setParentId(10L);
            }}, new TypeToken<CreateCommentRequest>() {
            }.getType());

    private final String testGetCommentRequest = this.gson.toJson(
            new GetCommentsByPostRequest() {{
                setPostId(1L);
            }}, new TypeToken<GetCommentsByPostRequest>() {
            }.getType());

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void testGetCommentsByPost() throws Exception {
        when(this.commentService.getCommentsByPost(any())).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(get("/comment/all/{postId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetCommentsByNotFoundPost() throws Exception {
        when(this.commentService.getCommentsByPost(any())).thenThrow(new NotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comment/all/{postId}", 123L);
        ResultActions actualPerformResult = this.mockMvc.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    @Test
    public void testUpdateComment() throws Exception {
        when(this.commentService.updateComment(any(), any())).thenReturn(1L);
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest();
        updateCommentRequest.setId(111L);
        updateCommentRequest.setText("Test text");
        String content = (new ObjectMapper()).writeValueAsString(updateCommentRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/update/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    public void testDeleteComment() throws Exception {
        doNothing().when(this.commentService).deleteComment(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/delete/{id}", 123L);
        this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateCommentForNonexistentPost() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        doThrow(new NotFoundException("")).when(commentService).createComment(any(), anyLong(), anyString(),anyLong());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/comment/create", this.testCreateCommentRequest);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.getStatus());
    }

    @Test
    public void testCreateCommentForExistsPost() throws Exception {
        long id = 1L;
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        when(commentService.createComment(any(), anyLong(), anyString(),anyLong())).thenReturn(id);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/comment/create", this.testCreateCommentRequest);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        Assertions.assertEquals(String.valueOf(id), actualResponse.getContent());
    }
}