package com.example.petshelper.controller;

import com.example.petshelper.controller.request.CreateSectionRequest;
import com.example.petshelper.exceptions.SectionAlreadyExistsException;
import com.example.petshelper.model.Section;
import com.example.petshelper.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

@RequestMapping("/section")
public class SectionController {
    @Autowired
    SectionService sectionService;

    @PostMapping("/create")
    public ResponseEntity createSection(@RequestBody CreateSectionRequest request) {
        //UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Section section = new Section();
        section.setName(request.getName());
        try {
            sectionService.saveSection(section);
        } catch (SectionAlreadyExistsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity("OK",HttpStatus.OK);
    }

}
