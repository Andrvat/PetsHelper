package com.example.petshelper.service;

import com.example.petshelper.model.Role;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserCredential saveUser(UserCredential userCredential, String role) {
        UserCredential userCredentialByEmailFromDB = userRepository.findByEmail(userCredential.getEmail());
        UserCredential userCredentialByUsernameFromDB = userRepository.findByUsername(userCredential.getUsername());
        if (userCredentialByEmailFromDB != null || userCredentialByUsernameFromDB != null)
            return null;
        Role userRole = null;
        if(role.equals("user")) userRole = new Role(Role.ROLE_USER_ID,Role.ROLE_USER);
        else if (role.equals("admin")) userRole = new Role(Role.ROLE_ADMIN_ID,Role.ROLE_ADMIN);
        userCredential.setRoles(Collections.singleton(userRole));
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        return userRepository.save(userCredential);

    }

    public UserCredential findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserCredential findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserCredential findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserCredential findByEmailAndPassword(String email, String password) {
        UserCredential userCredential = findByEmail(email);
        if (userCredential != null) {
            if (passwordEncoder.matches(password, userCredential.getPassword())) {
                return userCredential;
            }
        }
        return null;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}