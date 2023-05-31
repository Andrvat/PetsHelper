package com.example.petshelper.controller;

import com.example.petshelper.RequestResult;
import com.example.petshelper.TestsMockProvider;
import com.example.petshelper.controller.dto.PostDto;
import com.example.petshelper.mappers.PostMapper;
import com.example.petshelper.model.*;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.repository.PostRepository;
import com.example.petshelper.service.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private PersonalityService personalityService;

    @MockBean
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private TagService tagService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserService userService;

    private final Gson gson = new Gson();

    private final Post testPost = new Post() {{
        setId(1L);
        setHeader("Test header");
        setDescription("This is description of test post");
        setAuthor(new UserPersonality(new UserCredential() {{
            setUsername("Test user");
        }}));
        setDate(new Date());
        setIsActual(true);
        setCategory(new Category() {{
            setSection(new Section());
        }});
        setTags(new ArrayList<>());
        setImages(new LinkedList<>());
    }};

    private final String testPostJsonData =
            "{ " +
                    "\"header\" : \"I find my cat\", " +
                    "\"description\" : \"I lost my cat yesterday near my house. Please help me find it!\", " +
                    "\"category\" : \"Posts\", " +
                    "\"section\" : \"TestSection\", " +
                    "\"tags\" : \"#test_tag #test_taggg\", " +
                    "\"images\" : [] " +
                    "}";

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void testCreatePost() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        when(categoryService.findCategoryByRussianName(any())).thenReturn(new Category());
        when(tagService.getTagsByNames(any())).thenReturn(new ArrayList<>());
        when(postService.createPost(any(), any(),anyString(),any(),
                anyString(), anyString(), any(), any(), anyBoolean(), anyBoolean()))
                .thenReturn(this.testPost.getId());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/post/create", this.testPostJsonData);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetPostById() throws Exception {
        when(postRepository.getById(this.testPost.getId())).thenReturn(this.testPost);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/post/" + this.testPost.getId(), null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetAllModeratedPosts() throws Exception {
        when(postService.getModeratedPosts()).thenReturn(
                new ArrayList<>() {{
                    add(PostMapper.toDto(testPost));
                    add(PostMapper.toDto(testPost));
                }});
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/post/all", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        PostDto[] postDtos = this.gson.fromJson(actualResponse.getContent(), PostDto[].class);
        Assertions.assertEquals(2, postDtos.length);
        for (PostDto postDto : postDtos) {
            Assertions.assertEquals(this.testPost.getHeader(), postDto.getPostHeader());
            Assertions.assertEquals(this.testPost.getAuthor().getCredential().getUsername(), postDto.getAuthorUsername());
            Assertions.assertEquals(this.testPost.getDescription(), postDto.getDescription());
        }
    }

    @Test
    public void testGetPostsByCategory() throws Exception {
        when(postService.getPostByCategory(anyString())).thenReturn(
                new ArrayList<>() {{
                    add(PostMapper.toDto(testPost));
                    add(PostMapper.toDto(testPost));
                }});
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/post/category/cat", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        PostDto[] postDtos = this.gson.fromJson(actualResponse.getContent(), PostDto[].class);
        Assertions.assertEquals(2, postDtos.length);
        for (PostDto postDto : postDtos) {
            Assertions.assertEquals(this.testPost.getHeader(), postDto.getPostHeader());
            Assertions.assertEquals(this.testPost.getAuthor().getCredential().getUsername(), postDto.getAuthorUsername());
            Assertions.assertEquals(this.testPost.getDescription(), postDto.getDescription());
        }
    }

    @Test
    public void testGetAllPostsToModerate() throws Exception {
        when(postService.getPostsToModerate()).thenReturn(
                new ArrayList<>() {{
                    add(PostMapper.toDto(testPost));
                    add(PostMapper.toDto(testPost));
                }});
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/post/moderate/all", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        PostDto[] postDtos = this.gson.fromJson(actualResponse.getContent(), PostDto[].class);
        Assertions.assertEquals(2, postDtos.length);
        for (PostDto postDto : postDtos) {
            Assertions.assertEquals(this.testPost.getHeader(), postDto.getPostHeader());
            Assertions.assertEquals(this.testPost.getAuthor().getCredential().getUsername(), postDto.getAuthorUsername());
            Assertions.assertEquals(this.testPost.getDescription(), postDto.getDescription());
        }
    }

    @Test
    public void testGetPostsByTags() throws Exception {
        int objectsAmount = 10;
        String requestContent = "кошки#собаки";
        when(postService.getPostsByTags(anyString())).thenReturn(new ArrayList<>() {{
            for (int i = 0; i < objectsAmount; i++) {
                add(PostMapper.toDto(testPost));
            }
        }});
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/post/tags/" + requestContent, null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        PostDto[] posts = this.gson.fromJson(actualResponse.getContent(), PostDto[].class);
        Assertions.assertEquals(objectsAmount, posts.length);
    }

    @Test
    public void testSetPostAsModeratedByNotAdmin() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/post/" + this.testPost.getId() + "/moderated", null);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), actualResponse.getStatus());
    }

    @Test
    public void testSetPostAsModeratedByAdmin() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_ADMIN);
        doNothing().when(postService).setPostAsModerated(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/post/" + this.testPost.getId() + "/moderated", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testDeleteAnotherUserPost() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        when(postRepository.getById(any())).thenReturn(this.testPost);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/post/" + this.testPost.getId() + "/delete", null);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), actualResponse.getStatus());
    }

    @Test
    public void testDeletePost() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(this.testPost.getAuthor());
        when(postRepository.getById(any())).thenReturn(this.testPost);
        doNothing().when(postService).deletePost(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/post/" + this.testPost.getId() + "/delete", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testEditAnotherUserPost() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        when(postRepository.getById(any())).thenReturn(this.testPost);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/post/" + this.testPost.getId() + "/edit", this.testPostJsonData.replaceAll("Moscow", "Sochi"));
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), actualResponse.getStatus());
    }

    @Test
    public void testEditPost() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(this.testPost.getAuthor());
        when(postRepository.getById(any())).thenReturn(this.testPost);
        doNothing().when(postService).editPost(anyLong(), anyString(), anyString(), anyString(), anyString(),anyString());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/post/" + this.testPost.getId() + "/edit", this.testPostJsonData.replaceAll("Moscow", "Sochi"));
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }
}