package com.example.petshelper.service;

import com.example.petshelper.exceptions.CategoryAlreadyExistsException;
import com.example.petshelper.model.Category;
import com.example.petshelper.model.Section;
import com.example.petshelper.repository.CategoryRepository;
import com.example.petshelper.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public void saveCategory(Category category) throws CategoryAlreadyExistsException {
        Category categoryInDB = categoryRepository.findByName(category.getName());
        if(categoryInDB != null) throw new CategoryAlreadyExistsException("Category " + category.getName() + " already exists.");
        categoryRepository.save(category);
    }


    public Category getCategory(Long id) {
       return categoryRepository.getById(id);
    }

    public Category findCategoryByRussianName(String name) {
        return categoryRepository.findByRussianName(name);
    }
}
