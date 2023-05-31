package com.example.petshelper.service;


import com.example.petshelper.controller.dto.PersonalityDto;
import com.example.petshelper.controller.dto.PostDto;
import com.example.petshelper.exceptions.ForbiddenException;
import com.example.petshelper.exceptions.NotFoundException;
import com.example.petshelper.mappers.PersonalityMapper;
import com.example.petshelper.model.Post;
import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import com.example.petshelper.repository.PersonalityRepository;
import com.example.petshelper.repository.PostRepository;
import com.example.petshelper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Service
public class PersonalityService {

    @Autowired
    PersonalityRepository personalityRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    public UserPersonality findByCredential(UserCredential credential) {
        return personalityRepository.findUserPersonalitiesByCredential(credential);
    }

    public PersonalityDto getPersonality(Long userId) {
        UserPersonality personality =  personalityRepository.findUserPersonalitiesByCredential(userRepository.getById(userId));
        return PersonalityMapper.personalityToDto(personality, postRepository.countByAuthor(personality));
    }

    public PersonalityDto getPersonalityByUsername(String username) {
        UserPersonality personality = personalityRepository.findUserPersonalitiesByCredential(userRepository.findByUsername(username));
        return PersonalityMapper.personalityToDto(personality, postRepository.countByAuthor(personality));
    }

    public Long savePersonality(UserPersonality personality) {
        return personalityRepository.save(personality).getId();
    }

    public List<UserPersonality> getSubscriptions(Long personalityId) throws NotFoundException {
        Optional<UserPersonality> subscriber = personalityRepository.findById(personalityId);
        if (subscriber.isEmpty()) {
            throw new NotFoundException("user doesn't exist");
        }
        return personalityRepository.findAllBySubscribersContains(subscriber.get());
    }

    public List<UserPersonality> getSubscribers(Long personalityId) throws NotFoundException {
        Optional<UserPersonality> subscription = personalityRepository.findById(personalityId);
        if (subscription.isEmpty()) {
            throw new NotFoundException("user doesn't exist");
        }
        return personalityRepository.findAllBySubscriptionContains(subscription.get());
    }
    public void subscribe(Long myId, Long anotherUserId) throws NotFoundException, ForbiddenException {
        Optional<UserPersonality> me = personalityRepository.findById(myId);
        Optional<UserPersonality> anotherUser = personalityRepository.findById(anotherUserId);
        if (me.isEmpty()) {
            throw new ForbiddenException();
        }
        if (anotherUser.isEmpty()) {
            throw new NotFoundException("user doesn't exist");
        }
        me.get().getSubscription().add(anotherUser.get());
        personalityRepository.save(me.get());
    }

    public void unsubscribe(Long myId, Long anotherUserId) throws ForbiddenException, NotFoundException {
        Optional<UserPersonality> me = personalityRepository.findById(myId);
        Optional<UserPersonality> anotherUser = personalityRepository.findById(anotherUserId);
        if (me.isEmpty()) {
            throw new ForbiddenException();
        }
        if (anotherUser.isEmpty()) {
            throw new NotFoundException("user doesn't exist");
        }
        me.get().getSubscription().remove(anotherUser.get());
        personalityRepository.save(me.get());
    }
}
