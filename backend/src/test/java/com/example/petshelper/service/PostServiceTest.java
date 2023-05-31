package com.example.petshelper.service;

import com.example.petshelper.controller.PostController;
import com.example.petshelper.controller.dto.PostDto;
import com.example.petshelper.exceptions.CategoryAlreadyExistsException;
import com.example.petshelper.exceptions.PostDescriptionAlreadyExits;
import com.example.petshelper.mappers.PostMapper;
import com.example.petshelper.model.*;
import com.example.petshelper.repository.CategoryRepository;
import com.example.petshelper.repository.ImageRepository;
import com.example.petshelper.repository.PostRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private TagService tagService;

    @MockBean
    ImageRepository imageRepository;

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

    private final List<Post> testPostsList = new ArrayList<>() {{
        add(testPost);
        add(testPost);
        add(testPost);
    }};

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(postService);
    }

    @Test
    public void testCreatePost() {
        when(postRepository.save(any())).thenReturn(this.testPost);
        when(imageRepository.save(any())).thenReturn(null);
        Assertions.assertEquals(this.testPost.getId(), postService.createPost(
                new UserPersonality(),
                new Category(),
                "",
                new Date(),
                "",
                "",
                new ArrayList<>(),
                new LinkedList<>() {{
                    add("");
                }},
                true,
                true
        ));
    }

    @Test
    public void testDeletePost() {
        doNothing().when(postRepository).delete(any());
        Assertions.assertDoesNotThrow(() -> postService.deletePost(this.testPost));
    }

    @Test
    public void testGetPostByCategory() {
        Category category = new Category();
        category.setName("Помощь");
        when(categoryRepository.findByName(anyString())).thenReturn(category);
        when(postRepository.findByCategoryOrderByDate(category)).thenReturn(this.testPostsList);
        List<PostDto> postDtos = postService.getPostByCategory(category.getName());
        Assertions.assertEquals(this.testPostsList.size(), postDtos.size());
    }

    @Test
    public void testGetModeratedPosts() {
        when(postRepository.findByIsModerationPassedOrderByDate(true)).thenReturn(this.testPostsList);
        List<PostDto> postDtos = postService.getModeratedPosts();
        Assertions.assertEquals(this.testPostsList.size(), postDtos.size());
    }

    @Test
    public void testGetPostsToModerate() {
        when(postRepository.findByIsModerationPassedOrderByDate(false)).thenReturn(this.testPostsList);
        List<PostDto> postDtos = postService.getPostsToModerate();
        Assertions.assertEquals(this.testPostsList.size(), postDtos.size());
    }

    @Test
    public void testGetPostsByTags() {
        List<Long> tagIds = List.of(new Long[]{8L, 7L, 7L, 5L, 4L, 2L, 2L, 1L});
        List<Tag> tags = new ArrayList<>();
        for (Long tagId : tagIds) {
            Tag tag = new Tag();
            tag.setId(tagId);
            tags.add(tag);
        }
        int postsWithHighestMatchesNumber = 5;
        List<Object[]> scoredPosts = new ArrayList<>() {{
            for (Long tagId : tagIds) {
                add(new Object[]{tagId, testPost});
            }
        }};
        when(tagService.getTagsByNames(anyString())).thenReturn(tags);
        when(postRepository.findAllByTagsOrdered(tagIds)).thenReturn(scoredPosts);
        List<PostDto> postDtos = postService.getPostsByTags("");
        Assertions.assertEquals(postsWithHighestMatchesNumber, postDtos.size());
    }

    @Test
    public void testSetPostAsModerated() {
        long id = 1L;
        when(postRepository.getById(id)).thenReturn(this.testPost);
        when(postRepository.save(any())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> postService.setPostAsModerated(id));
    }

    @Test
    public void testEditPost() {
        long id = 1L;
        when(postRepository.getById(id)).thenReturn(this.testPost);
        when(categoryRepository.getById(id)).thenReturn(this.testPost.getCategory());
        when(postRepository.save(any())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> postService.editPost(id, "", "", "", "",""));
    }

    @Test
    public void testGetPostsByAuthor() {
        UserPersonality userPersonality = new UserPersonality();
        when(postRepository.findByAuthor(userPersonality)).thenReturn(this.testPostsList);
        Assertions.assertEquals(this.testPostsList.size(), postService.getPostsByAuthor(userPersonality).size());
    }
}