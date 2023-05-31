package com.example.petshelper.controller;

import com.example.petshelper.RequestResult;
import com.example.petshelper.TestsMockProvider;
import com.example.petshelper.exceptions.SectionAlreadyExistsException;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.service.SectionService;
import com.example.petshelper.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(controllers = SectionController.class)
class SectionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SectionService sectionService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserService userService;

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(mockMvc);
    }

    private String getTestSectionJsonData() {
        return "{ \"name\" : \"TestSection\"}";
    }

    @Test
    public void testSaveAlreadyExistsSection() throws Exception {
        doThrow(new SectionAlreadyExistsException("")).when(sectionService).saveSection(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/section/create", this.getTestSectionJsonData());
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), actualResponse.getStatus());
    }

    @Test
    public void testSaveSection() throws Exception {
        doNothing().when(sectionService).saveSection(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/section/create", this.getTestSectionJsonData());
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

}