package com.example.petshelper.service;

import com.example.petshelper.controller.dto.CommentDto;
import com.example.petshelper.exceptions.NotFoundException;
import com.example.petshelper.model.Category;
import com.example.petshelper.model.Comment;
import com.example.petshelper.model.Post;
import com.example.petshelper.model.Section;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.repository.CommentRepository;
import com.example.petshelper.repository.PersonalityRepository;
import com.example.petshelper.repository.PostRepository;
import com.example.petshelper.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    CommentRepository commentRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PersonalityRepository personalityRepository;

    @MockBean
    PostRepository postRepository;

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(commentService);
    }

//    @Test
//    public void testCreateComment() throws NotFoundException {
//        UserPersonality userPersonality = new UserPersonality();
//
//        Category category = new Category();
//        category.setId(123L);
//        category.setName("Bella");
//        category.setRussianName("Бэлла");
//        category.setSection(new Section());
//
//        Post post = new Post();
//        post.setAuthor(userPersonality);
//        post.setCategory(category);
//        post.setDate(new Date());
//        post.setId(123L);
//        post.setImages(new ArrayList<>());
//        post.setTags(new ArrayList<>());
//        Optional<Post> ofResult = Optional.of(post);
//        when(this.postRepository.findById(org.mockito.Mockito.any())).thenReturn(ofResult);
//
//        UserCredential userCredential1 = new UserCredential();
//        userCredential1.setEmail("jane.doe@example.org");
//        userCredential1.setId(123L);
//        userCredential1.setPassword("iloveyou");
//        userCredential1.setRoles(new HashSet<>());
//        userCredential1.setUsername("janedoe");
//
//        UserPersonality userPersonality1 = new UserPersonality();
//        userPersonality1.setAboutMe("About Me");
//        userPersonality1.setAge(1);
//        userPersonality1.setCity("Oxford");
//        userPersonality1.setCredential(userCredential1);
//        userPersonality1.setFirstName("Jane");
//        userPersonality1.setGender("Gender");
//        userPersonality1.setId(123L);
//        userPersonality1.setLastName("Doe");
//        userPersonality1.setPhoneNumber("4105551212");
//        userPersonality1.setPosts(new ArrayList<>());
//        userPersonality1.setRating(1);
//        userPersonality1.setSubscribers(new ArrayList<>());
//        userPersonality1.setSubscription(new ArrayList<>());
//
//        UserCredential userCredential2 = new UserCredential();
//        userCredential2.setEmail("jane.doe@example.org");
//        userCredential2.setId(123L);
//        userCredential2.setPassword("iloveyou");
//        userCredential2.setRoles(new HashSet<>());
//        userCredential2.setUsername("janedoe");
//
//        UserPersonality userPersonality2 = new UserPersonality();
//        userPersonality2.setAboutMe("About Me");
//        userPersonality2.setAge(1);
//        userPersonality2.setCity("Oxford");
//        userPersonality2.setCredential(userCredential2);
//        userPersonality2.setFirstName("Jane");
//        userPersonality2.setGender("Gender");
//        userPersonality2.setId(123L);
//        userPersonality2.setLastName("Doe");
//        userPersonality2.setPhoneNumber("4105551212");
//        userPersonality2.setPosts(new ArrayList<>());
//        userPersonality2.setRating(1);
//        userPersonality2.setSubscribers(new ArrayList<>());
//        userPersonality2.setSubscription(new ArrayList<>());
//
//        Section section1 = new Section();
//        section1.setId(123L);
//        section1.setName("Bella");
//
//        Category category1 = new Category();
//        category1.setId(123L);
//        category1.setName("Bella");
//        category1.setRussianName("Bella");
//        category1.setSection(section1);
//
//        Post post1 = new Post();
//        post1.setAuthor(userPersonality2);
//        post1.setCategory(category1);
//        post1.setCity("Oxford");
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        post1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        post1.setDescription("The characteristics of someone or something");
//        post1.setHeader("Header");
//        post1.setId(123L);
//        post1.setImages(new ArrayList<>());
//        post1.setIsActual(true);
//        post1.setIsModerationPassed(true);
//        post1.setTags(new ArrayList<>());
//
//        Comment comment = new Comment();
//        comment.setAuthor(userPersonality1);
//        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        comment.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
//        comment.setId(123L);
//        comment.setParentId(123L);
//        comment.setPost(post1);
//        comment.setText("Text");
//        when(this.commentRepository.save((Comment) org.mockito.Mockito.any())).thenReturn(comment);
//
//        UserCredential userCredential3 = new UserCredential();
//        userCredential3.setEmail("jane.doe@example.org");
//        userCredential3.setId(123L);
//        userCredential3.setPassword("iloveyou");
//        userCredential3.setRoles(new HashSet<>());
//        userCredential3.setUsername("janedoe");
//
//        UserPersonality userPersonality3 = new UserPersonality();
//        userPersonality3.setAboutMe("About Me");
//        userPersonality3.setAge(1);
//        userPersonality3.setCity("Oxford");
//        userPersonality3.setCredential(userCredential3);
//        userPersonality3.setFirstName("Jane");
//        userPersonality3.setGender("Gender");
//        userPersonality3.setId(123L);
//        userPersonality3.setLastName("Doe");
//        userPersonality3.setPhoneNumber("4105551212");
//        userPersonality3.setPosts(new ArrayList<>());
//        userPersonality3.setRating(1);
//        userPersonality3.setSubscribers(new ArrayList<>());
//        userPersonality3.setSubscription(new ArrayList<>());
//        assertEquals(123L, this.commentService.createComment(userPersonality3, 123L, "Text", 123L).longValue());
//        verify(this.postRepository).findById((Long) org.mockito.Mockito.any());
//        verify(this.commentRepository).save((Comment) org.mockito.Mockito.any());
//    }
//
//    /**
//     * Method under test: {@link CommentService#createComment(UserPersonality, Long, String, Long)}
//     */
//    @Test
//    void testCreateComment2() throws NotFoundException {
//        when(this.postRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(Optional.empty());
//
//        UserCredential userCredential = new UserCredential();
//        userCredential.setEmail("jane.doe@example.org");
//        userCredential.setId(123L);
//        userCredential.setPassword("iloveyou");
//        userCredential.setRoles(new HashSet<>());
//        userCredential.setUsername("janedoe");
//
//        UserPersonality userPersonality = new UserPersonality();
//        userPersonality.setAboutMe("About Me");
//        userPersonality.setAge(1);
//        userPersonality.setCity("Oxford");
//        userPersonality.setCredential(userCredential);
//        userPersonality.setFirstName("Jane");
//        userPersonality.setGender("Gender");
//        userPersonality.setId(123L);
//        userPersonality.setLastName("Doe");
//        userPersonality.setPhoneNumber("4105551212");
//        userPersonality.setPosts(new ArrayList<>());
//        userPersonality.setRating(1);
//        userPersonality.setSubscribers(new ArrayList<>());
//        userPersonality.setSubscription(new ArrayList<>());
//
//        UserCredential userCredential1 = new UserCredential();
//        userCredential1.setEmail("jane.doe@example.org");
//        userCredential1.setId(123L);
//        userCredential1.setPassword("iloveyou");
//        userCredential1.setRoles(new HashSet<>());
//        userCredential1.setUsername("janedoe");
//
//        UserPersonality userPersonality1 = new UserPersonality();
//        userPersonality1.setAboutMe("About Me");
//        userPersonality1.setAge(1);
//        userPersonality1.setCity("Oxford");
//        userPersonality1.setCredential(userCredential1);
//        userPersonality1.setFirstName("Jane");
//        userPersonality1.setGender("Gender");
//        userPersonality1.setId(123L);
//        userPersonality1.setLastName("Doe");
//        userPersonality1.setPhoneNumber("4105551212");
//        userPersonality1.setPosts(new ArrayList<>());
//        userPersonality1.setRating(1);
//        userPersonality1.setSubscribers(new ArrayList<>());
//        userPersonality1.setSubscription(new ArrayList<>());
//
//        Section section = new Section();
//        section.setId(123L);
//        section.setName("Bella");
//
//        Category category = new Category();
//        category.setId(123L);
//        category.setName("Bella");
//        category.setRussianName("Bella");
//        category.setSection(section);
//
//        Post post = new Post();
//        post.setAuthor(userPersonality1);
//        post.setCategory(category);
//        post.setCity("Oxford");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        post.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        post.setDescription("The characteristics of someone or something");
//        post.setHeader("Header");
//        post.setId(123L);
//        post.setImages(new ArrayList<>());
//        post.setIsActual(true);
//        post.setIsModerationPassed(true);
//        post.setTags(new ArrayList<>());
//
//        Comment comment = new Comment();
//        comment.setAuthor(userPersonality);
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        comment.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        comment.setId(123L);
//        comment.setParentId(123L);
//        comment.setPost(post);
//        comment.setText("Text");
//        when(this.commentRepository.save((Comment) org.mockito.Mockito.any())).thenReturn(comment);
//
//        UserCredential userCredential2 = new UserCredential();
//        userCredential2.setEmail("jane.doe@example.org");
//        userCredential2.setId(123L);
//        userCredential2.setPassword("iloveyou");
//        userCredential2.setRoles(new HashSet<>());
//        userCredential2.setUsername("janedoe");
//
//        UserPersonality userPersonality2 = new UserPersonality();
//        userPersonality2.setAboutMe("About Me");
//        userPersonality2.setAge(1);
//        userPersonality2.setCity("Oxford");
//        userPersonality2.setCredential(userCredential2);
//        userPersonality2.setFirstName("Jane");
//        userPersonality2.setGender("Gender");
//        userPersonality2.setId(123L);
//        userPersonality2.setLastName("Doe");
//        userPersonality2.setPhoneNumber("4105551212");
//        userPersonality2.setPosts(new ArrayList<>());
//        userPersonality2.setRating(1);
//        userPersonality2.setSubscribers(new ArrayList<>());
//        userPersonality2.setSubscription(new ArrayList<>());
//        assertThrows(NotFoundException.class,
//                () -> this.commentService.createComment(userPersonality2, 123L, "Text", 123L));
//        verify(this.postRepository).findById((Long) org.mockito.Mockito.any());
//    }
//
//    /**
//     * Method under test: {@link CommentService#getCommentsByPost(Long)}
//     */
//    @Test
//    void testGetCommentsByPost() throws NotFoundException {
//        UserCredential userCredential = new UserCredential();
//        userCredential.setEmail("jane.doe@example.org");
//        userCredential.setId(123L);
//        userCredential.setPassword("iloveyou");
//        userCredential.setRoles(new HashSet<>());
//        userCredential.setUsername("janedoe");
//
//        UserPersonality userPersonality = new UserPersonality();
//        userPersonality.setAboutMe("About Me");
//        userPersonality.setAge(1);
//        userPersonality.setCity("Oxford");
//        userPersonality.setCredential(userCredential);
//        userPersonality.setFirstName("Jane");
//        userPersonality.setGender("Gender");
//        userPersonality.setId(123L);
//        userPersonality.setLastName("Doe");
//        userPersonality.setPhoneNumber("4105551212");
//        userPersonality.setPosts(new ArrayList<>());
//        userPersonality.setRating(1);
//        userPersonality.setSubscribers(new ArrayList<>());
//        userPersonality.setSubscription(new ArrayList<>());
//
//        Section section = new Section();
//        section.setId(123L);
//        section.setName("Bella");
//
//        Category category = new Category();
//        category.setId(123L);
//        category.setName("Bella");
//        category.setRussianName("Bella");
//        category.setSection(section);
//
//        Post post = new Post();
//        post.setAuthor(userPersonality);
//        post.setCategory(category);
//        post.setCity("Oxford");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        post.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        post.setDescription("The characteristics of someone or something");
//        post.setHeader("Header");
//        post.setId(123L);
//        post.setImages(new ArrayList<>());
//        post.setIsActual(true);
//        post.setIsModerationPassed(true);
//        post.setTags(new ArrayList<>());
//        Optional<Post> ofResult = Optional.of(post);
//        when(this.postRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);
//        when(this.commentRepository.findAllByPostId((Long) org.mockito.Mockito.any())).thenReturn(new ArrayList<>());
//        assertTrue(this.commentService.getCommentsByPost(123L).isEmpty());
//        verify(this.postRepository).findById((Long) org.mockito.Mockito.any());
//        verify(this.commentRepository).findAllByPostId((Long) org.mockito.Mockito.any());
//    }
//
//    /**
//     * Method under test: {@link CommentService#getCommentsByPost(Long)}
//     */
//    @Test
//    void testGetCommentsByPost2() throws NotFoundException {
//        when(this.postRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(Optional.empty());
//        when(this.commentRepository.findAllByPostId((Long) org.mockito.Mockito.any())).thenReturn(new ArrayList<>());
//        assertThrows(NotFoundException.class, () -> this.commentService.getCommentsByPost(123L));
//        verify(this.postRepository).findById((Long) org.mockito.Mockito.any());
//    }
//
//    /**
//     * Method under test: {@link CommentService#getCommentsByPost(Long)}
//     */
//    @Test
//    void testGetCommentsByPost3() throws NotFoundException {
//        UserCredential userCredential = new UserCredential();
//        userCredential.setEmail("jane.doe@example.org");
//        userCredential.setId(123L);
//        userCredential.setPassword("iloveyou");
//        userCredential.setRoles(new HashSet<>());
//        userCredential.setUsername("janedoe");
//
//        UserPersonality userPersonality = new UserPersonality();
//        userPersonality.setAboutMe("About Me");
//        userPersonality.setAge(1);
//        userPersonality.setCity("Oxford");
//        userPersonality.setCredential(userCredential);
//        userPersonality.setFirstName("Jane");
//        userPersonality.setGender("Gender");
//        userPersonality.setId(123L);
//        userPersonality.setLastName("Doe");
//        userPersonality.setPhoneNumber("4105551212");
//        userPersonality.setPosts(new ArrayList<>());
//        userPersonality.setRating(1);
//        userPersonality.setSubscribers(new ArrayList<>());
//        userPersonality.setSubscription(new ArrayList<>());
//
//        Section section = new Section();
//        section.setId(123L);
//        section.setName("Bella");
//
//        Category category = new Category();
//        category.setId(123L);
//        category.setName("Bella");
//        category.setRussianName("Bella");
//        category.setSection(section);
//
//        Post post = new Post();
//        post.setAuthor(userPersonality);
//        post.setCategory(category);
//        post.setCity("Oxford");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        post.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        post.setDescription("The characteristics of someone or something");
//        post.setHeader("Header");
//        post.setId(123L);
//        post.setImages(new ArrayList<>());
//        post.setIsActual(true);
//        post.setIsModerationPassed(true);
//        post.setTags(new ArrayList<>());
//        Optional<Post> ofResult = Optional.of(post);
//        when(this.postRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);
//
//        UserCredential userCredential1 = new UserCredential();
//        userCredential1.setEmail("jane.doe@example.org");
//        userCredential1.setId(123L);
//        userCredential1.setPassword("iloveyou");
//        userCredential1.setRoles(new HashSet<>());
//        userCredential1.setUsername("janedoe");
//
//        UserPersonality userPersonality1 = new UserPersonality();
//        userPersonality1.setAboutMe("About Me");
//        userPersonality1.setAge(1);
//        userPersonality1.setCity("Oxford");
//        userPersonality1.setCredential(userCredential1);
//        userPersonality1.setFirstName("Jane");
//        userPersonality1.setGender("Gender");
//        userPersonality1.setId(123L);
//        userPersonality1.setLastName("Doe");
//        userPersonality1.setPhoneNumber("4105551212");
//        userPersonality1.setPosts(new ArrayList<>());
//        userPersonality1.setRating(1);
//        userPersonality1.setSubscribers(new ArrayList<>());
//        userPersonality1.setSubscription(new ArrayList<>());
//
//        UserCredential userCredential2 = new UserCredential();
//        userCredential2.setEmail("jane.doe@example.org");
//        userCredential2.setId(123L);
//        userCredential2.setPassword("iloveyou");
//        userCredential2.setRoles(new HashSet<>());
//        userCredential2.setUsername("janedoe");
//
//        UserPersonality userPersonality2 = new UserPersonality();
//        userPersonality2.setAboutMe("About Me");
//        userPersonality2.setAge(1);
//        userPersonality2.setCity("Oxford");
//        userPersonality2.setCredential(userCredential2);
//        userPersonality2.setFirstName("Jane");
//        userPersonality2.setGender("Gender");
//        userPersonality2.setId(123L);
//        userPersonality2.setLastName("Doe");
//        userPersonality2.setPhoneNumber("4105551212");
//        userPersonality2.setPosts(new ArrayList<>());
//        userPersonality2.setRating(1);
//        userPersonality2.setSubscribers(new ArrayList<>());
//        userPersonality2.setSubscription(new ArrayList<>());
//
//        Section section1 = new Section();
//        section1.setId(123L);
//        section1.setName("Bella");
//
//        Category category1 = new Category();
//        category1.setId(123L);
//        category1.setName("Bella");
//        category1.setRussianName("Bella");
//        category1.setSection(section1);
//
//        Post post1 = new Post();
//        post1.setAuthor(userPersonality2);
//        post1.setCategory(category1);
//        post1.setCity("Oxford");
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        post1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        post1.setDescription("The characteristics of someone or something");
//        post1.setHeader("Header");
//        post1.setId(123L);
//        post1.setImages(new ArrayList<>());
//        post1.setIsActual(true);
//        post1.setIsModerationPassed(true);
//        post1.setTags(new ArrayList<>());
//
//        Comment comment = new Comment();
//        comment.setAuthor(userPersonality1);
//        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        comment.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
//        comment.setId(123L);
//        comment.setParentId(123L);
//        comment.setPost(post1);
//        comment.setText("Text");
//
//        ArrayList<Comment> commentList = new ArrayList<>();
//        commentList.add(comment);
//        when(this.commentRepository.findAllByPostId((Long) org.mockito.Mockito.any())).thenReturn(commentList);
//        assertEquals(1, this.commentService.getCommentsByPost(123L).size());
//        verify(this.postRepository).findById((Long) org.mockito.Mockito.any());
//        verify(this.commentRepository).findAllByPostId((Long) org.mockito.Mockito.any());
//    }
//
//    /**
//     * Method under test: {@link CommentService#updateComment(Long, String)}
//     */
//    @Test
//    void testUpdateComment() {
//        UserCredential userCredential = new UserCredential();
//        userCredential.setEmail("jane.doe@example.org");
//        userCredential.setId(123L);
//        userCredential.setPassword("iloveyou");
//        userCredential.setRoles(new HashSet<>());
//        userCredential.setUsername("janedoe");
//
//        UserPersonality userPersonality = new UserPersonality();
//        userPersonality.setAboutMe("About Me");
//        userPersonality.setAge(1);
//        userPersonality.setCity("Oxford");
//        userPersonality.setCredential(userCredential);
//        userPersonality.setFirstName("Jane");
//        userPersonality.setGender("Gender");
//        userPersonality.setId(123L);
//        userPersonality.setLastName("Doe");
//        userPersonality.setPhoneNumber("4105551212");
//        userPersonality.setPosts(new ArrayList<>());
//        userPersonality.setRating(1);
//        userPersonality.setSubscribers(new ArrayList<>());
//        userPersonality.setSubscription(new ArrayList<>());
//
//        UserCredential userCredential1 = new UserCredential();
//        userCredential1.setEmail("jane.doe@example.org");
//        userCredential1.setId(123L);
//        userCredential1.setPassword("iloveyou");
//        userCredential1.setRoles(new HashSet<>());
//        userCredential1.setUsername("janedoe");
//
//        UserPersonality userPersonality1 = new UserPersonality();
//        userPersonality1.setAboutMe("About Me");
//        userPersonality1.setAge(1);
//        userPersonality1.setCity("Oxford");
//        userPersonality1.setCredential(userCredential1);
//        userPersonality1.setFirstName("Jane");
//        userPersonality1.setGender("Gender");
//        userPersonality1.setId(123L);
//        userPersonality1.setLastName("Doe");
//        userPersonality1.setPhoneNumber("4105551212");
//        userPersonality1.setPosts(new ArrayList<>());
//        userPersonality1.setRating(1);
//        userPersonality1.setSubscribers(new ArrayList<>());
//        userPersonality1.setSubscription(new ArrayList<>());
//
//        Section section = new Section();
//        section.setId(123L);
//        section.setName("Bella");
//
//        Category category = new Category();
//        category.setId(123L);
//        category.setName("Bella");
//        category.setRussianName("Bella");
//        category.setSection(section);
//
//        Post post = new Post();
//        post.setAuthor(userPersonality1);
//        post.setCategory(category);
//        post.setCity("Oxford");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        post.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        post.setDescription("The characteristics of someone or something");
//        post.setHeader("Header");
//        post.setId(123L);
//        post.setImages(new ArrayList<>());
//        post.setIsActual(true);
//        post.setIsModerationPassed(true);
//        post.setTags(new ArrayList<>());
//
//        Comment comment = new Comment();
//        comment.setAuthor(userPersonality);
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        comment.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        comment.setId(123L);
//        comment.setParentId(123L);
//        comment.setPost(post);
//        comment.setText("Text");
//
//        UserCredential userCredential2 = new UserCredential();
//        userCredential2.setEmail("jane.doe@example.org");
//        userCredential2.setId(123L);
//        userCredential2.setPassword("iloveyou");
//        userCredential2.setRoles(new HashSet<>());
//        userCredential2.setUsername("janedoe");
//
//        UserPersonality userPersonality2 = new UserPersonality();
//        userPersonality2.setAboutMe("About Me");
//        userPersonality2.setAge(1);
//        userPersonality2.setCity("Oxford");
//        userPersonality2.setCredential(userCredential2);
//        userPersonality2.setFirstName("Jane");
//        userPersonality2.setGender("Gender");
//        userPersonality2.setId(123L);
//        userPersonality2.setLastName("Doe");
//        userPersonality2.setPhoneNumber("4105551212");
//        userPersonality2.setPosts(new ArrayList<>());
//        userPersonality2.setRating(1);
//        userPersonality2.setSubscribers(new ArrayList<>());
//        userPersonality2.setSubscription(new ArrayList<>());
//
//        UserCredential userCredential3 = new UserCredential();
//        userCredential3.setEmail("jane.doe@example.org");
//        userCredential3.setId(123L);
//        userCredential3.setPassword("iloveyou");
//        userCredential3.setRoles(new HashSet<>());
//        userCredential3.setUsername("janedoe");
//
//        UserPersonality userPersonality3 = new UserPersonality();
//        userPersonality3.setAboutMe("About Me");
//        userPersonality3.setAge(1);
//        userPersonality3.setCity("Oxford");
//        userPersonality3.setCredential(userCredential3);
//        userPersonality3.setFirstName("Jane");
//        userPersonality3.setGender("Gender");
//        userPersonality3.setId(123L);
//        userPersonality3.setLastName("Doe");
//        userPersonality3.setPhoneNumber("4105551212");
//        userPersonality3.setPosts(new ArrayList<>());
//        userPersonality3.setRating(1);
//        userPersonality3.setSubscribers(new ArrayList<>());
//        userPersonality3.setSubscription(new ArrayList<>());
//
//        Section section1 = new Section();
//        section1.setId(123L);
//        section1.setName("Bella");
//
//        Category category1 = new Category();
//        category1.setId(123L);
//        category1.setName("Bella");
//        category1.setRussianName("Bella");
//        category1.setSection(section1);
//
//        Post post1 = new Post();
//        post1.setAuthor(userPersonality3);
//        post1.setCategory(category1);
//        post1.setCity("Oxford");
//        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        post1.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
//        post1.setDescription("The characteristics of someone or something");
//        post1.setHeader("Header");
//        post1.setId(123L);
//        post1.setImages(new ArrayList<>());
//        post1.setIsActual(true);
//        post1.setIsModerationPassed(true);
//        post1.setTags(new ArrayList<>());
//
//        Comment comment1 = new Comment();
//        comment1.setAuthor(userPersonality2);
//        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        comment1.setDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
//        comment1.setId(123L);
//        comment1.setParentId(123L);
//        comment1.setPost(post1);
//        comment1.setText("Text");
//        when(this.commentRepository.save((Comment) org.mockito.Mockito.any())).thenReturn(comment1);
//        when(this.commentRepository.getById((Long) org.mockito.Mockito.any())).thenReturn(comment);
//        assertEquals(123L, this.commentService.updateComment(123L, "Text").longValue());
//        verify(this.commentRepository).getById((Long) org.mockito.Mockito.any());
//        verify(this.commentRepository).save((Comment) org.mockito.Mockito.any());
//    }
//
//    /**
//     * Method under test: {@link CommentService#deleteComment(Long)}
//     */
//    @Test
//    void testDeleteComment() {
//        UserCredential userCredential = new UserCredential();
//        userCredential.setEmail("jane.doe@example.org");
//        userCredential.setId(123L);
//        userCredential.setPassword("iloveyou");
//        userCredential.setRoles(new HashSet<>());
//        userCredential.setUsername("janedoe");
//
//        UserPersonality userPersonality = new UserPersonality();
//        userPersonality.setAboutMe("About Me");
//        userPersonality.setAge(1);
//        userPersonality.setCity("Oxford");
//        userPersonality.setCredential(userCredential);
//        userPersonality.setFirstName("Jane");
//        userPersonality.setGender("Gender");
//        userPersonality.setId(123L);
//        userPersonality.setLastName("Doe");
//        userPersonality.setPhoneNumber("4105551212");
//        userPersonality.setPosts(new ArrayList<>());
//        userPersonality.setRating(1);
//        userPersonality.setSubscribers(new ArrayList<>());
//        userPersonality.setSubscription(new ArrayList<>());
//
//        UserCredential userCredential1 = new UserCredential();
//        userCredential1.setEmail("jane.doe@example.org");
//        userCredential1.setId(123L);
//        userCredential1.setPassword("iloveyou");
//        userCredential1.setRoles(new HashSet<>());
//        userCredential1.setUsername("janedoe");
//
//        UserPersonality userPersonality1 = new UserPersonality();
//        userPersonality1.setAboutMe("About Me");
//        userPersonality1.setAge(1);
//        userPersonality1.setCity("Oxford");
//        userPersonality1.setCredential(userCredential1);
//        userPersonality1.setFirstName("Jane");
//        userPersonality1.setGender("Gender");
//        userPersonality1.setId(123L);
//        userPersonality1.setLastName("Doe");
//        userPersonality1.setPhoneNumber("4105551212");
//        userPersonality1.setPosts(new ArrayList<>());
//        userPersonality1.setRating(1);
//        userPersonality1.setSubscribers(new ArrayList<>());
//        userPersonality1.setSubscription(new ArrayList<>());
//
//        Section section = new Section();
//        section.setId(123L);
//        section.setName("Bella");
//
//        Category category = new Category();
//        category.setId(123L);
//        category.setName("Bella");
//        category.setRussianName("Bella");
//        category.setSection(section);
//
//        Post post = new Post();
//        post.setAuthor(userPersonality1);
//        post.setCategory(category);
//        post.setCity("Oxford");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        post.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        post.setDescription("The characteristics of someone or something");
//        post.setHeader("Header");
//        post.setId(123L);
//        post.setImages(new ArrayList<>());
//        post.setIsActual(true);
//        post.setIsModerationPassed(true);
//        post.setTags(new ArrayList<>());
//
//        Comment comment = new Comment();
//        comment.setAuthor(userPersonality);
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        comment.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        comment.setId(123L);
//        comment.setParentId(123L);
//        comment.setPost(post);
//        comment.setText("Text");
//        when(this.commentRepository.getById((Long) org.mockito.Mockito.any())).thenReturn(comment);
//        doNothing().when(this.commentRepository).delete((Comment) org.mockito.Mockito.any());
//        this.commentService.deleteComment(123L);
//        verify(this.commentRepository).getById((Long) org.mockito.Mockito.any());
//        verify(this.commentRepository).delete((Comment) org.mockito.Mockito.any());
//    }

    @Test
    public void testCreateCommentForNonexistentPost() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> commentService.createComment(
                new UserPersonality(), 1L, "",10L));
    }

    @Test
    public void testCreateCommentForExistsPost() throws NotFoundException {
        Comment comment = new Comment();
        comment.setId(1L);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(new Post()));
        when(commentRepository.save(any())).thenReturn(comment);
        Assertions.assertEquals(comment.getId(), commentService.createComment(
                new UserPersonality(), 2L, "",10L));
    }

    @Test
    public void testGetCommentByNonexistentPost() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> commentService.getCommentsByPost(1L));
    }

    @Test
    public void testGetCommentByExistsPost() throws NotFoundException {
        long commentsAmount = 10;
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(new Post()));
        List<Comment> commentsList = new ArrayList<>();
        for (long i = 0; i < commentsAmount; ++i) {
            Comment comment = new Comment();
            comment.setId(i);
            comment.setAuthor(new UserPersonality() {{
                setCredential(new UserCredential());
            }});
            comment.setDate(new Date());
            commentsList.add(comment);
        }
        long id = 1L;
        when(commentRepository.findAllByPostId(id)).thenReturn(commentsList);
        List<CommentDto> commentDtos = commentService.getCommentsByPost(id);
        Assertions.assertEquals(commentsList.size(), commentDtos.size());
        for (int i = 0; i < commentDtos.size(); ++i) {
            Assertions.assertEquals(commentsList.get(i).getId(), commentDtos.get(i).getId());
        }
    }

    @Test
    public void testDeleteComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        when(commentRepository.getById(comment.getId())).thenReturn(comment);
        doNothing().when(commentRepository).delete(any());
        Assertions.assertDoesNotThrow(() -> commentService.deleteComment(comment.getId()));
    }

    @Test
    public void testUpdateComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Abc");
        when(commentRepository.getById(comment.getId())).thenReturn(comment);
        when(commentRepository.save(any())).thenReturn(comment);
        Assertions.assertEquals(comment.getId(), commentService.updateComment(comment.getId(), "New abc"));
    }
}