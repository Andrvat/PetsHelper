package com.example.petshelper.service;

import com.example.petshelper.model.UserCredential;
import com.example.petshelper.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCredential getTestUserCredential() {
        return new UserCredential() {{
            setId(1L);
            setUsername("Abc");
            setEmail("abc@mail.ru");
            setPassword("12345qwerty");
        }};
    }

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(userService);
    }

    @Test
    public void testSaveUserWhenEmailAlreadyInUse() {
        when(userRepository.findByEmail(any())).thenReturn(new UserCredential());
        when(userRepository.findByUsername(any())).thenReturn(null);
        UserCredential answer = userService.saveUser(new UserCredential(), "user");
        Assertions.assertNull(answer);
    }

    @Test
    public void testSaveUserWhenUsernameAlreadyInUse() {
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(userRepository.findByUsername(any())).thenReturn(new UserCredential());
        UserCredential answer = userService.saveUser(new UserCredential(), "user");
        Assertions.assertNull(answer);
    }

    @Test
    public void testSaveUserWithRoleUser() {
        UserCredential userCredential = new UserCredential();
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(userRepository.findByUsername(any())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(userCredential);
        UserCredential answer = userService.saveUser(this.getTestUserCredential(), "user");
        Assertions.assertEquals(userCredential, answer);
    }

    @Test
    public void testSaveUserWithRoleAdmin() {
        UserCredential userCredential = new UserCredential();
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(userRepository.findByUsername(any())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(userCredential);
        UserCredential answer = userService.saveUser(this.getTestUserCredential(), "admin");
        Assertions.assertEquals(userCredential, answer);
    }

    @Test
    public void testUserFoundById() {
        Long id = this.getTestUserCredential().getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(this.getTestUserCredential()));
        UserCredential resultCredential = userService.findById(id);
        Assertions.assertNotNull(resultCredential);
        Assertions.assertEquals(id, resultCredential.getId());
    }

    @Test
    public void testUserFoundByEmail() {
        String email = this.getTestUserCredential().getEmail();
        when(userRepository.findByEmail(email)).thenReturn(this.getTestUserCredential());
        UserCredential resultCredential = userService.findByEmail(email);
        Assertions.assertNotNull(resultCredential);
        Assertions.assertEquals(email, resultCredential.getEmail());
    }

    @Test
    public void testUserFoundByUsername() {
        String username = this.getTestUserCredential().getUsername();
        when(userRepository.findByUsername(username)).thenReturn(this.getTestUserCredential());
        UserCredential resultCredential = userService.findByUsername(username);
        Assertions.assertNotNull(resultCredential);
        Assertions.assertEquals(username, resultCredential.getUsername());
    }

    @Test
    public void testUserFoundByEmailAndPassword() {
        String email = this.getTestUserCredential().getEmail();
        String password = this.getTestUserCredential().getPassword();
        UserCredential encodedCredential = this.getTestUserCredential();
        encodedCredential.setPassword(userService.getPasswordEncoder().encode(password));
        when(userRepository.findByEmail(email)).thenReturn(encodedCredential);
        UserCredential resultCredential = userService.findByEmailAndPassword(email, password);
        Assertions.assertEquals(encodedCredential, resultCredential);
    }

    @Test
    public void testUserNotFoundByEmailAndPassword() {
        String email = this.getTestUserCredential().getEmail();
        String password = this.getTestUserCredential().getPassword();
        when(userRepository.findByEmail(email)).thenReturn(null);
        UserCredential resultCredential = userService.findByEmailAndPassword(email, password);
        Assertions.assertNull(resultCredential);
    }
}