package com.example.petshelper.controller;

import com.example.petshelper.model.Tag;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.service.PersonalityService;
import com.example.petshelper.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@CrossOrigin(origins = "http://localhost:3000")
public class TagController {

    private final TagService tagService;

    private final PersonalityService personalityService;

    TagController(TagService tagService, PersonalityService personalityService) {
        this.tagService = tagService;
        this.personalityService = personalityService;
    }

    @PostMapping("/create")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Object> createTag(@RequestBody String value) {
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPersonality personality = personalityService.findByCredential(user);
        Long tagId = tagService.createTag(value);
        return new ResponseEntity<>(tagId, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/getTagByPart/{part}")
    public ResponseEntity<Object> getTagByPart(@PathVariable("part") String part) {
        List<Tag> tags = tagService.getTagByPart(part);
        if (tags.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tagService.getTagByPart(part), HttpStatus.OK);
    }
}
