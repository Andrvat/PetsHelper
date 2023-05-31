package com.example.petshelper.mappers;

import com.example.petshelper.controller.dto.PersonalityDto;
import com.example.petshelper.model.UserPersonality;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonalityMapper {
    public static PersonalityDto personalityToDto(UserPersonality personality, Long postsNumber) {
        PersonalityDto personalityDto = new PersonalityDto();
        personalityDto.setId(personality.getId());
        personalityDto.setUsername(personality.getCredential().getUsername());
        personalityDto.setFirstName(personality.getFirstName());
        personalityDto.setLastName(personality.getLastName());
        personalityDto.setCity(personality.getCity());
        personalityDto.setPostsNumber(postsNumber);
        personalityDto.setEmail(personality.getCredential().getEmail());
        personalityDto.setPhoneNumber(personality.getPhoneNumber());
        personalityDto.setAboutMe(personality.getAboutMe());
        return personalityDto;
    }
}
