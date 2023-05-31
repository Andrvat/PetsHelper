package com.example.petshelper.controller;

import com.example.petshelper.controller.dto.PostDto;
import com.example.petshelper.controller.request.CreatePostRequest;
import com.example.petshelper.controller.request.EditPostRequest;
import com.example.petshelper.controller.response.PostResponse;
import com.example.petshelper.model.*;
import com.example.petshelper.exceptions.PostDescriptionAlreadyExits;
import com.example.petshelper.mappers.PostMapper;
import com.example.petshelper.model.*;
import com.example.petshelper.repository.PostRepository;
import com.example.petshelper.service.CategoryService;
import com.example.petshelper.service.PersonalityService;
import com.example.petshelper.service.PostService;
import com.example.petshelper.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    private final PostRepository postRepository;
    private final PersonalityService personalityService;
    private final CategoryService categoryService;
    private final PostService postService;
    private final TagService tagService;

    public PostController(PostRepository postRepository, PersonalityService personalityService, CategoryService categoryService, PostService postService, TagService tagService) {
        this.postRepository = postRepository;
        this.personalityService = personalityService;
        this.categoryService = categoryService;
        this.postService = postService;
        this.tagService = tagService;
    }


    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(produces = "application/json", value = "/create")
    public ResponseEntity<Object> createPost(@RequestBody CreatePostRequest request) {
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPersonality personality = personalityService.findByCredential(user);
        Category category = categoryService.findCategoryByRussianName(request.getCategory());
        List<Tag> tags = tagService.getTagsByNames(request.getTags());
        long id;
        id = postService.createPost(personality, category, personality.getCity(), new Date(), request.getHeader(),
                request.getDescription(), tags, request.getImages(), true, false);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/{id}")
    public PostDto getPost(@PathVariable Long id) {
        return PostMapper.toDto(postRepository.getById(id));
    }

    @GetMapping(produces = "application/json", value = "/all")
    public List<PostDto> getModeratedPosts() {
        return postService.getModeratedPosts();
    }

    @GetMapping("/subscriptions/posts")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<PostDto> getSubscriptionsPosts() {
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPersonality personality = personalityService.findByCredential(user);
        return postService.getSubscriptionsPosts(personality);
    }

    @GetMapping(produces = "application/json", value = "/category/{categoryName}")
    public List<PostDto> getPostsByCategory(@PathVariable String categoryName) {
        return postService.getPostByCategory(categoryName);
    }

    @GetMapping(produces = "application/json", value = "/moderate/all")
    public List<PostDto> getAllPostsToModerate() {
        return postService.getPostsToModerate();
    }

    @GetMapping(produces = "application/json", value = "/tags/{tags}")
    public List<PostDto> getPostsByTags(@PathVariable String tags) {
        return postService.getPostsByTags(tags);
    }

    @PostMapping(produces = "application/json", value = "{id}/moderated")
    public ResponseEntity<HttpStatus> setPostAsModerated(@PathVariable Long id) {
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.isAdmin()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        postService.setPostAsModerated(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", value = "{id}/edit")
    public ResponseEntity<Object> editPost(@PathVariable Long id, @RequestBody EditPostRequest editPostRequest) {
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPersonality personality = personalityService.findByCredential(user);

        Post post = postRepository.getById(id);

        if (post.getAuthor() != personality && !user.isAdmin()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        postService.editPost(id, editPostRequest.getPostHeader(), editPostRequest.getDescription(),
                editPostRequest.getCity(), editPostRequest.getCategoryName(), editPostRequest.getTags());
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", value = "{id}/delete")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long id) {
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPersonality personality = personalityService.findByCredential(user);

        Post post = postRepository.getById(id);

        if (post.getAuthor() != personality && !user.isAdmin()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        postService.deletePost(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
