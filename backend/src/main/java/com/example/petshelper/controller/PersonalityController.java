package com.example.petshelper.controller;

import com.example.petshelper.controller.dto.PersonalityDto;
import com.example.petshelper.exceptions.ForbiddenException;
import com.example.petshelper.exceptions.NotFoundException;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.repository.UserRepository;
import com.example.petshelper.service.PersonalityService;
import com.example.petshelper.service.PostService;
import com.example.petshelper.service.UserService;
import com.example.petshelper.controller.request.UpdatePersonalityRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/personality")
public class PersonalityController {
    @Autowired
    private PersonalityService personalityService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository repository;

    @PostMapping(produces = "application/json", consumes = {"application/json"}, value = "/update")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> updatePersonality(@RequestBody UpdatePersonalityRequest request) {
        UserCredential credential = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserCredential userWithSameEmail = userService.findByEmail(request.getEmail());
        UserCredential userWithSameUsername = userService.findByUsername(request.getUsername());
        if ((userWithSameEmail != null && !Objects.equals(credential.getId(), userWithSameEmail.getId())) ||
                (userWithSameUsername != null && !Objects.equals(credential.getId(), userWithSameUsername.getId()))) {
            return new ResponseEntity<>(new Long(-1), HttpStatus.NO_CONTENT);
        }
        credential.setUsername(request.getUsername());
        UserPersonality personality = personalityService.findByCredential(credential);
        if (personality == null) {
            personality = new UserPersonality(credential);
        }

        personality.setFirstName(request.getFirstName());
        personality.setLastName(request.getLastName());
        personality.setAboutMe(request.getAboutMe());
        personality.setGender(request.getGender());
        personality.setAge(request.getAge());
        personality.setPhoneNumber(request.getPhoneNumber());
        personality.getCredential().setEmail(request.getEmail());
        repository.save(credential);

        Long id = personalityService.savePersonality(personality);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PersonalityDto getPersonality(@PathVariable Long userId) {
        return personalityService.getPersonality(userId);
    }

    @GetMapping("/username/{username}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public PersonalityDto getPersonalityByUsername(@PathVariable String username) {
        return personalityService.getPersonalityByUsername(username);
    }

    @PostMapping("/subscribe/{anotherUserId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> subscribe(@PathVariable Long anotherUserId) {
        UserCredential meCredential = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPersonality me = personalityService.findByCredential(meCredential);
        try {
            personalityService.subscribe(me.getId(), anotherUserId);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unsubscribe/{anotherUserId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> unsubscribe(@PathVariable Long anotherUserId) {
        UserCredential meCredential = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPersonality me = personalityService.findByCredential(meCredential);
        try {
            personalityService.unsubscribe(me.getId(), anotherUserId);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/subscriptions/{userId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> getSubscriptions(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(personalityService.getSubscriptions(userId), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subscribers/{userId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> getSubscribers(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(personalityService.getSubscribers(userId), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subscriptions/number/{userId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> getSubscriptionsNumber(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(personalityService.getSubscriptions(userId).size(), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subscribers/number/{userId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> getSubscribersNumber(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(personalityService.getSubscribers(userId).size(), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
