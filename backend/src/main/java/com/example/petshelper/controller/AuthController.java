package com.example.petshelper.controller;


import com.example.petshelper.controller.request.*;
import com.example.petshelper.controller.response.JwtResponse;
import com.example.petshelper.controller.response.RegistrationResponse;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.repository.PersonalityRepository;
import com.example.petshelper.service.PersonalityService;
import com.example.petshelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    public static final String USER_ALREADY_REGISTER = "User already registered";
    public static final String USERNAME_ALREADY_TAKEN = "Username already taken";

    @Autowired
    private UserService userService;

    @Autowired
    private PersonalityService personalityService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public RegistrationResponse registerUser(@RequestBody RegistrationRequest registrationRequest) {
        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(registrationRequest.getUsername());
        userCredential.setPassword(registrationRequest.getPassword());
        userCredential.setEmail(registrationRequest.getEmail());
        UserCredential savedUser = userService.saveUser(userCredential, registrationRequest.getRole());
        if (savedUser == null) {
            return new RegistrationResponse(false, USER_ALREADY_REGISTER);
        }
        UserPersonality personality = new UserPersonality(savedUser);
        personality.setCity(registrationRequest.getCity());
        personalityService.savePersonality(personality);
        return new RegistrationResponse(true, "");
    }

    @PostMapping("/auth")
    public JwtResponse auth(@RequestBody AuthRequest request) {
        UserCredential userCredential = userService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (userCredential != null) {
            if(userCredential.isAdmin()) {
                return new JwtResponse(jwtProvider.generateRefreshToken(userCredential.getEmail()),
                        userCredential.getUsername(), true,true);
            }
            else {
                return new JwtResponse(jwtProvider.generateRefreshToken(userCredential.getEmail()),
                        userCredential.getUsername(),true,false);
            }
        }
        return new JwtResponse("", null,false,false);
    }
}