package com.example.petshelper.provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    private final String testEmail = "abc@mail.ru";

    @Test
    @Order(1)
    public void testContextLoading() {
        Assertions.assertNotNull(jwtProvider);
    }

    @Test
    @Order(2)
    public void testGenerateRefreshToken() {
        String token = jwtProvider.generateRefreshToken(this.testEmail);
        Assertions.assertNotNull(token);
    }

    @Test
    @Order(3)
    public void testGetEmailFromAuthToken() {
        String token = jwtProvider.generateRefreshToken(this.testEmail);
        String email = jwtProvider.getEmailFromAuthToken(token);
        Assertions.assertEquals(this.testEmail, email);
    }

    @Test
    @Order(4)
    public void testValidateNotAuthToken() {
        Assertions.assertFalse(jwtProvider.validateAuthToken("not-a-token"));
    }

    @Test
    @Order(5)
    public void testValidateAuthToken() {
        String token = jwtProvider.generateRefreshToken(this.testEmail);
        Assertions.assertTrue(jwtProvider.validateAuthToken(token));
    }
}