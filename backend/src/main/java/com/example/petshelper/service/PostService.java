package com.example.petshelper.service;

import com.example.petshelper.controller.dto.PostDto;
import com.example.petshelper.mappers.PostMapper;
import com.example.petshelper.model.*;
import com.example.petshelper.repository.CategoryRepository;
import com.example.petshelper.repository.ImageRepository;
import com.example.petshelper.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagService tagService;

    @Autowired
    ImageRepository imageRepository;

    public long createPost(UserPersonality personality, Category category, String city, Date date, String header,
                           String desc, List<Tag> tags, List<String> images, boolean isActual, boolean isModerated) {
        Post post = new Post();
        post.setAuthor(personality);
        post.setCategory(category);
        post.setCity(city);
        post.setDate(date);
        post.setHeader(header);
        post.setDescription(desc);
        post.setTags(tags);
        post.setIsActual(isActual);
        post.setIsModerationPassed(isModerated);

        post = postRepository.save(post);

        List<Image> imagesByteList = new LinkedList<>();
        if(images != null) {
            for (var image : images) {
                Image imageToSave = new Image(image);
                imageToSave.setPost(post);
                imageRepository.save(imageToSave);
                imagesByteList.add(imageToSave);
            }
            post.setImages(imagesByteList);
        }
        post = postRepository.save(post);
        return post.getId();
    }


    public void deletePost(Post post) {
        postRepository.delete(post);
    }


    public List<PostDto> getPostByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        List<Post> posts = postRepository.findByCategoryOrderByDate(category);
        return PostMapper.toDto(posts);
    }

    public List<PostDto> getModeratedPosts() {
        List<Post> posts = postRepository.findByIsModerationPassedOrderByDate(true);
        return PostMapper.toDto(posts);
    }

    public List<PostDto> getPostsToModerate() {
        List<Post> posts = postRepository.findByIsModerationPassedOrderByDate(false);
        return PostMapper.toDto(posts);
    }

    public List<PostDto> getPostsByTags(String stringOfTags) {
        List<Tag> tags = tagService.getTagsByNames(stringOfTags);
        List<Long> tagsIds = tags.stream().map(Tag::getId).collect(Collectors.toList());
        List<Object[]> postsWithSearchScore = postRepository.findAllByTagsOrdered(tagsIds);
        if(postsWithSearchScore.size() == 0) return new ArrayList<>();
        List<Post> posts = new LinkedList<>();
        final long maxScore = (long) postsWithSearchScore.get(0)[0];
        final long scoreRange = 5;
        for (Object[] postWithScore : postsWithSearchScore) {
            if ((long) postWithScore[0] < maxScore - scoreRange) {
                break;
            }
            posts.add((Post) postWithScore[1]);
        }
        return PostMapper.toDto(posts);
    }

    public void setPostAsModerated(Long id) {
        Post post = postRepository.getById(id);
        post.setIsModerationPassed(true);
        postRepository.save(post);
    }

    public void editPost(Long id, String header, String desc, String city, String categoryName, String stringOfTags) {
        Post post = postRepository.getById(id);
        post.setHeader(header);
        post.setDescription(desc);
        post.setCity(city);
        post.setCategory(categoryRepository.findByRussianName(categoryName));
        List<Tag> tags = tagService.getTagsByNames(stringOfTags);
        post.setTags(tags);

        postRepository.save(post);
    }

    public List<Post> getPostsByAuthor(UserPersonality personality) {
        return postRepository.findByAuthor(personality);
    }

    public List<PostDto> getSubscriptionsPosts(UserPersonality me) {
        return PostMapper.toDto(postRepository.getSubscriptionsPosts(me.getId()));
    }
}
