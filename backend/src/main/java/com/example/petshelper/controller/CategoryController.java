package com.example.petshelper.controller;

import com.example.petshelper.controller.request.CreateCategoryRequest;
import com.example.petshelper.exceptions.CategoryAlreadyExistsException;
import com.example.petshelper.model.Category;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.service.CategoryService;
import com.example.petshelper.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/category")

public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    SectionService sectionService;

    @PostMapping("/create")
    public ResponseEntity createCategory(@RequestBody CreateCategoryRequest request) {
        //UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Category category = new Category();
        category.setName(request.getName());
        category.setSection(sectionService.getSection(request.getSectionId()));

        try {
            categoryService.saveCategory(category);
        } catch (CategoryAlreadyExistsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity("OK",HttpStatus.OK);

    }


}
