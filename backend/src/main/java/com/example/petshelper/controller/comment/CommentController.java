package com.example.petshelper.controller.comment;

import com.example.petshelper.controller.request.CreateCommentRequest;
import com.example.petshelper.controller.request.GetCommentsByPostRequest;
import com.example.petshelper.controller.request.UpdateCommentRequest;
import com.example.petshelper.exceptions.NotFoundException;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.service.CommentService;
import com.example.petshelper.service.PersonalityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    PersonalityService personalityService;

    @PostMapping("/create")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> createComment(@RequestBody CreateCommentRequest request) {
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPersonality personality = personalityService.findByCredential(user);
        Long commentId;
        try {
            commentId = commentService.createComment(personality, request.getPostId(), request.getText(), request.getParentId());
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/all/{postId}")
    public ResponseEntity<Object> getCommentsByPost(@PathVariable Long postId) {
        try {
            return new ResponseEntity<>(commentService.getCommentsByPost(postId), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(produces = "application/json", value = "update/")
    public ResponseEntity<Object> updateComment(@RequestBody UpdateCommentRequest request) {
        Long id = commentService.updateComment(request.getId(), request.getText());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping(value = "delete/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
