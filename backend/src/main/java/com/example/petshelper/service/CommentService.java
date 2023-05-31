package com.example.petshelper.service;

import com.example.petshelper.controller.dto.CommentDto;
import com.example.petshelper.exceptions.NotFoundException;
import com.example.petshelper.model.Comment;
import com.example.petshelper.model.Post;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.repository.CommentRepository;
import com.example.petshelper.repository.PersonalityRepository;
import com.example.petshelper.repository.PostRepository;
import com.example.petshelper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonalityRepository personalityRepository;

    @Autowired
    PostRepository postRepository;

    public Long createComment(UserPersonality personality, Long postId, String text, Long parentId) throws NotFoundException {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new NotFoundException("Post " + postId + " not found");
        }
        Comment comment = new Comment(personality, post.get(), text, new Date(), parentId);


        comment = commentRepository.save(comment);
        return comment.getId();
    }

    public List<CommentDto> getCommentsByPost(Long postId) throws NotFoundException {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new NotFoundException("Post " + postId + " not found");
        }
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream().map((comment) -> {
            UserPersonality author = comment.getAuthor();
            return new CommentDto(comment.getId(), comment.getText(), comment.getDate().toString(),
                    author.getCredential().getUsername(), author.getId(), comment.getParentId());
        }).collect(Collectors.toList());
    }

    public Long updateComment(Long id, String text) {
        Comment comment = commentRepository.getById(id);
        comment.setText(text);

        return commentRepository.save(comment).getId();
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.getById(id);
        commentRepository.delete(comment);
    }
}
