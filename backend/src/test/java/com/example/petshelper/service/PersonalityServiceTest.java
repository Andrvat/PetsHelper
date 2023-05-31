package com.example.petshelper.service;

import com.example.petshelper.controller.dto.PersonalityDto;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.repository.PersonalityRepository;
import com.example.petshelper.repository.PostRepository;
import com.example.petshelper.repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonalityServiceTest {

    @Autowired
    private PersonalityService personalityService;

    @MockBean
    private PersonalityRepository personalityRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(personalityService);
    }

    @Test
    public void testFindByCredential() {
        UserPersonality userPersonality = new UserPersonality();
        when(personalityRepository.findUserPersonalitiesByCredential(any())).thenReturn(userPersonality);
        Assertions.assertEquals(userPersonality, personalityService.findByCredential(new UserCredential()));
    }

    @Test
    public void testSavePersonality() {
        long id = 1L;
        UserPersonality userPersonality = new UserPersonality();
        userPersonality.setId(id);
        when(personalityRepository.save(any())).thenReturn(userPersonality);
        Assertions.assertEquals(id, personalityService.savePersonality(userPersonality));
    }

    @Test
    public void testGetPersonalityByUsername() {
        UserCredential userCredential = new UserCredential();
        UserPersonality userPersonality = new UserPersonality();
        userPersonality.setFirstName("Test");
        userPersonality.setLastName("Testov");
        userPersonality.setCredential(new UserCredential());
        when(userRepository.findByUsername(any())).thenReturn(userCredential);
        when(personalityRepository.findUserPersonalitiesByCredential(userCredential)).thenReturn(userPersonality);
        when(postRepository.countByAuthor(userPersonality)).thenReturn(0L);
        PersonalityDto personalityDto = personalityService.getPersonalityByUsername("Username");
        Assertions.assertEquals(userPersonality.getFirstName(), personalityDto.getFirstName());
        Assertions.assertEquals(userPersonality.getLastName(), personalityDto.getLastName());
    }

    @Test
    public void testGetPersonality() {
        UserCredential userCredential = new UserCredential();
        UserPersonality userPersonality = new UserPersonality();
        userPersonality.setFirstName("Test");
        userPersonality.setLastName("Testov");
        userPersonality.setCredential(new UserCredential());
        when(userRepository.getById(any())).thenReturn(userCredential);
        when(personalityRepository.findUserPersonalitiesByCredential(userCredential)).thenReturn(userPersonality);
        when(postRepository.countByAuthor(userPersonality)).thenReturn(0L);
        PersonalityDto personalityDto = personalityService.getPersonality(1L);
        Assertions.assertEquals(userPersonality.getFirstName(), personalityDto.getFirstName());
        Assertions.assertEquals(userPersonality.getLastName(), personalityDto.getLastName());
    }

}