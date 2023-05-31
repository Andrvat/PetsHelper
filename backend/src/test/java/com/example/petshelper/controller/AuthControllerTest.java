package com.example.petshelper.controller;

import com.example.petshelper.RequestResult;
import com.example.petshelper.TestsMockProvider;
import com.example.petshelper.controller.request.AuthRequest;
import com.example.petshelper.controller.request.RegistrationRequest;
import com.example.petshelper.controller.response.JwtResponse;
import com.example.petshelper.controller.response.RegistrationResponse;
import com.example.petshelper.model.Role;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.service.PersonalityService;
import com.example.petshelper.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PersonalityService personalityService;

    @MockBean
    private JwtProvider jwtProvider;

    private final Gson gson = new Gson();

    private final String getUserRegistrationJsonData = this.gson.toJson(
            new RegistrationRequest() {{
                setUsername("TestUsername");
                setEmail("testov@mail.ru");
                setPassword("12345qwerty");
                setCity("Moscow");
                setRole("user");
            }}, new TypeToken<RegistrationRequest>(){}.getType());

    private final String getUserAuthJsonDate = this.gson.toJson(
            new AuthRequest() {{
                setEmail("testov@mail.ru");
                setPassword("2345qwerty");
            }},  new TypeToken<AuthRequest>(){}.getType());

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void testRegisterAlreadyRegisteredUser() throws Exception {
        when(userService.saveUser(any(), any())).thenReturn(null);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/register", this.getUserRegistrationJsonData);
        RegistrationResponse registrationResponse = this.gson.fromJson(actualResponse.getContent(), RegistrationResponse.class);
        Assertions.assertFalse(registrationResponse.isOk());
        Assertions.assertEquals(AuthController.USER_ALREADY_REGISTER, registrationResponse.getError());
    }

    @Test
    public void testRegisterUser() throws Exception {
        when(userService.saveUser(any(), any())).thenReturn(new UserCredential());
        when(personalityService.savePersonality(any())).thenReturn(null);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/register", this.getUserRegistrationJsonData);
        RegistrationResponse registrationResponse = this.gson.fromJson(actualResponse.getContent(), RegistrationResponse.class);
        Assertions.assertTrue(registrationResponse.isOk());
    }

    @Test
    public void testAuthWhenUserNotFound() throws Exception {
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(null);
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/auth", this.getUserAuthJsonDate);
        JwtResponse jwtResponse = this.gson.fromJson(actualResponse.getContent(), JwtResponse.class);
        Assertions.assertFalse(jwtResponse.getStatus());
        Assertions.assertNull(jwtResponse.getUsername());
    }

    @Test
    public void testAuthWhenAdminExists() throws Exception {
        UserCredential userCredential = new UserCredential();
        userCredential.setUsername("Test");
        userCredential.setRoles(new HashSet<>() {{
            add(new Role() {{
                setName(Role.ROLE_ADMIN);
            }});
        }});
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(userCredential);
        when(jwtProvider.generateRefreshToken(any())).thenReturn("token");
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/auth", this.getUserAuthJsonDate);
        JwtResponse jwtResponse = this.gson.fromJson(actualResponse.getContent(), JwtResponse.class);
        Assertions.assertTrue(jwtResponse.getStatus());
        Assertions.assertTrue(jwtResponse.getIsAdmin());
        Assertions.assertEquals(userCredential.getUsername(), jwtResponse.getUsername());
    }

    @Test
    public void testAuthWhenUserExists() throws Exception {
        UserCredential userCredential = new UserCredential();
        userCredential.setUsername("Test");
        userCredential.setRoles(new HashSet<>() {{
            add(new Role() {{
                setName(Role.ROLE_USER);
            }});
        }});
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(userCredential);
        when(jwtProvider.generateRefreshToken(any())).thenReturn("token");
        RequestResult actualResponse = TestsMockProvider.getResultFromParametrizedPostRequest(
                mockMvc, "/auth", this.getUserAuthJsonDate);
        JwtResponse jwtResponse = this.gson.fromJson(actualResponse.getContent(), JwtResponse.class);
        Assertions.assertTrue(jwtResponse.getStatus());
        Assertions.assertFalse(jwtResponse.getIsAdmin());
        Assertions.assertEquals(userCredential.getUsername(), jwtResponse.getUsername());
    }
}