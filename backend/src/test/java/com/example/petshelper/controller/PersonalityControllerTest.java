package com.example.petshelper.controller;

import com.example.petshelper.RequestResult;
import com.example.petshelper.TestsMockProvider;
import com.example.petshelper.controller.dto.PersonalityDto;
import com.example.petshelper.controller.request.CreateCategoryRequest;
import com.example.petshelper.controller.request.UpdatePersonalityRequest;
import com.example.petshelper.controller.response.RegistrationResponse;
import com.example.petshelper.exceptions.ForbiddenException;
import com.example.petshelper.exceptions.NotFoundException;
import com.example.petshelper.exceptions.SectionAlreadyExistsException;
import com.example.petshelper.model.Role;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.repository.UserRepository;
import com.example.petshelper.service.PersonalityService;
import com.example.petshelper.service.PostService;
import com.example.petshelper.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PersonalityController.class)
class PersonalityControllerTest {
    @Autowired
    private PersonalityController personalityController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonalityService personalityService;

    @MockBean
    private UserService userService;

    @MockBean
    private PostService postService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtProvider jwtProvider;

    private final Gson gson = new Gson();

    private final UpdatePersonalityRequest testPersonalityRequest = new UpdatePersonalityRequest() {{
        setUsername("TestUsername");
        setFirstName("Test");
        setLastName("Testov");
        setAge(20);
        setCity("Moscow");
        setPhoneNumber("+78005553535");
        setEmail("abc@mail.ru");
        setAboutMe("About me is empty");
        setGender("MALE");
    }};

    private final String testPersonalityRequestJsonData = this.gson.toJson(
            this.testPersonalityRequest, new TypeToken<UpdatePersonalityRequest>() {
            }.getType());

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void testNotFoundSubscribe() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        doThrow(new NotFoundException("")).when(personalityService).subscribe(any(), anyLong());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/personality/subscribe/1", null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.getStatus());
    }

    @Test
    public void testForbiddenSubscribe() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        doThrow(new ForbiddenException()).when(personalityService).subscribe(any(), anyLong());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/personality/subscribe/1", null);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), actualResponse.getStatus());
    }

    @Test
    public void testOkSubscribe() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        doNothing().when(personalityService).subscribe(any(), anyLong());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/personality/subscribe/1", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testNotFoundUnsubscribe() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        doThrow(new NotFoundException("")).when(personalityService).unsubscribe(any(), anyLong());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/personality/unsubscribe/1", null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.getStatus());
    }

    @Test
    public void testForbiddenUnsubscribe() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        doThrow(new ForbiddenException()).when(personalityService).unsubscribe(any(), anyLong());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/personality/unsubscribe/1", null);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), actualResponse.getStatus());
    }

    @Test
    public void testOkUnsubscribe() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER);
        when(personalityService.findByCredential(any())).thenReturn(new UserPersonality());
        doNothing().when(personalityService).unsubscribe(any(), anyLong());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/personality/unsubscribe/1", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testUpdatePersonalityByAnotherUser() throws Exception {
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER, 1L);
        when(userService.findByEmail(any())).thenReturn(new UserCredential() {{
            setId(2L);
        }});
        when(userService.findByUsername(any())).thenReturn(null);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/personality/update", this.testPersonalityRequestJsonData);
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), actualResponse.getStatus());
    }


    @Test
    public void testUpdatePersonality() throws Exception {
        long id = 2L;
        TestsMockProvider.setUpMockSpringAuth(Role.ROLE_USER, 1L);
        when(userService.findByEmail(any())).thenReturn(null);
        when(userService.findByUsername(any())).thenReturn(null);
        when(personalityService.findByCredential(any())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(null);
        when(personalityService.savePersonality(any())).thenReturn(id);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/personality/update", this.testPersonalityRequestJsonData);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        Assertions.assertEquals(id, this.gson.fromJson(actualResponse.getContent(), Long.class));
    }

    @Test
    public void testGetPersonality() throws Exception {
        long id = 1L;
        PersonalityDto personalityDto = new PersonalityDto() {{
            setId(id);
        }};
        when(personalityService.getPersonality(id)).thenReturn(personalityDto);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/" + id, null);
        PersonalityDto personalityDtoResponse = this.gson.fromJson(actualResponse.getContent(), PersonalityDto.class);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        Assertions.assertEquals(id, personalityDtoResponse.getId());
    }

    @Test
    public void testGetPersonalityByUsername() throws Exception {
        String username = this.testPersonalityRequest.getUsername();
        PersonalityDto personalityDto = new PersonalityDto() {{
            setUsername(username);
        }};
        when(personalityService.getPersonalityByUsername(username)).thenReturn(personalityDto);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/username/" + username, null);
        PersonalityDto personalityDtoResponse = this.gson.fromJson(actualResponse.getContent(), PersonalityDto.class);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
        Assertions.assertEquals(username, personalityDtoResponse.getUsername());
    }

    @Test
    public void testGetNotFoundSubscriptions() throws Exception {
        doThrow(new NotFoundException("")).when(personalityService).getSubscriptions(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/subscriptions/1", null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetSubscriptions() throws Exception {
        when(personalityService.getSubscriptions(any())).thenReturn(new LinkedList<>());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/subscriptions/1", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetNotFoundSubscribes() throws Exception {
        doThrow(new NotFoundException("")).when(personalityService).getSubscribers(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/subscribers/1", null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetSubscribes() throws Exception {
        when(personalityService.getSubscribers(any())).thenReturn(new LinkedList<>());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/subscribers/1", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetNotFoundSubscriptionsNumber() throws Exception {
        doThrow(new NotFoundException("")).when(personalityService).getSubscriptions(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/subscriptions/number/1", null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetSubscriptionsNumber() throws Exception {
        when(personalityService.getSubscribers(any())).thenReturn(new LinkedList<>());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/subscriptions/number/1", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetNotFoundSubscribersNumber() throws Exception {
        doThrow(new NotFoundException("")).when(personalityService).getSubscribers(any());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/subscribers/number/1", null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponse.getStatus());
    }

    @Test
    public void testGetSubscribersNumber() throws Exception {
        when(personalityService.getSubscribers(any())).thenReturn(new LinkedList<>());
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedGetRequest(
                mockMvc, "/personality/subscribers/number/1", null);
        Assertions.assertEquals(HttpStatus.OK.value(), actualResponse.getStatus());
    }
}