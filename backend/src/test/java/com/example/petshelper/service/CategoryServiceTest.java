package com.example.petshelper.service;

import com.example.petshelper.exceptions.CategoryAlreadyExistsException;
import com.example.petshelper.model.Category;
import com.example.petshelper.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    private final Category testCategory = new Category() {{
        setId(1L);
        setName("Cat");
    }};

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(categoryService);
    }

    @Test
    void testGetCategory() {
        Long id = this.testCategory.getId();
        when(categoryRepository.getById(id)).thenReturn(this.testCategory);
        Assertions.assertEquals(this.testCategory, categoryService.getCategory(id));
    }

    @Test
    void testSaveAlreadyExistCategory() {
        when(categoryRepository.findByName(any())).thenReturn(new Category());
        Assertions.assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.saveCategory(this.testCategory));
    }

    @Test
    void testSaveCategory() {
        when(categoryRepository.findByName(any())).thenReturn(null);
        when(categoryRepository.save(any())).thenReturn(this.testCategory);
        Assertions.assertDoesNotThrow(() -> categoryService.saveCategory(this.testCategory));
    }

    @Test
    void testFindCategoryByRussianName() {
        String name = this.testCategory.getName();
        when(categoryRepository.findByRussianName(name)).thenReturn(this.testCategory);
        Assertions.assertEquals(this.testCategory, categoryService.findCategoryByRussianName(name));
    }

}