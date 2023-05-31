package com.example.petshelper.controller;

import com.example.petshelper.RequestResult;
import com.example.petshelper.TestsMockProvider;
import com.example.petshelper.controller.request.CreateCategoryRequest;
import com.example.petshelper.controller.request.RegistrationRequest;
import com.example.petshelper.exceptions.CategoryAlreadyExistsException;
import com.example.petshelper.model.Section;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.service.CategoryService;
import com.example.petshelper.service.SectionService;
import com.example.petshelper.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private SectionService sectionService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserService userService;

    private final String getTestCategoryJsonData = new Gson().toJson(
            new CreateCategoryRequest() {{
                setName("TestSection");
                setSectionId(1L);
            }}, new TypeToken<CreateCategoryRequest>() {}.getType());

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void testCreateAlreadyExistsCategory() throws Exception {
        when(sectionService.getSection(any())).thenReturn(new Section());
        doThrow(new CategoryAlreadyExistsException("")).when(categoryService).saveCategory(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/category/create", this.getTestCategoryJsonData);
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), actualResponse.getStatus());
    }

    @Test
    public void testSaveCategory() throws Exception {
        when(sectionService.getSection(any())).thenReturn(new Section());
        doNothing().when(categoryService).saveCategory(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/category/create", this.getTestCategoryJsonData);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }
}