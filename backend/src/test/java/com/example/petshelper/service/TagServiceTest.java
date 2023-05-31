package com.example.petshelper.service;

import com.example.petshelper.model.Tag;
import com.example.petshelper.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
class TagServiceTest {

    @Autowired
    private TagService tagService;

    @MockBean
    private TagRepository tagRepository;

    private final Tag testTag = new Tag() {{
        setId(1L);
        setName("кот");
    }};

    private final List<Tag> testMatches = new ArrayList<>() {{
        add(new Tag("кот"));
        add(new Tag("котята"));
        add(new Tag("котенок"));
    }};

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(tagService);
    }

    @Test
    public void testCreateAlreadyExistTag() {
        when(tagRepository.findByName(this.testTag.getName())).thenReturn(Optional.of(this.testTag));
        when(tagRepository.save(any())).thenReturn(null);
        Assertions.assertEquals(this.testTag.getId(), tagService.createTag(this.testTag.getName()));
    }

    @Test
    public void testCreateTag() {
        when(tagRepository.findByName(this.testTag.getName())).thenReturn(Optional.empty());
        when(tagRepository.save(any())).thenReturn(this.testTag);
        Assertions.assertEquals(this.testTag.getId(), tagService.createTag(this.testTag.getName()));
    }

    @Test
    public void testGetTagByPart() {
        when(tagRepository.findAllByNameContains(this.testTag.getName())).thenReturn(this.testMatches);
        Assertions.assertEquals(this.testMatches.size(), tagService.getTagByPart(this.testTag.getName()).size());
    }

    @Test
    public void testGetTags() {
        List<Long> ids = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
        }};
        when(tagRepository.findAllById(ids)).thenReturn(this.testMatches);
        Assertions.assertEquals(this.testMatches.size(), tagService.getTags(ids).size());
    }

    @Test
    public void testGetTagsByNames() {
        String stringOfTags = "#кошки#собаки";
        List<String> tags = new ArrayList<>(List.of(new String[]{"#кошки", "#собаки"}));
        List<Long> tagIds = new ArrayList<>(List.of(new Long[]{1L, 2L}));
        for (int i = 0; i < tags.size(); ++i) {
            int finalI = i;
            Tag tag = new Tag() {{
                setId(tagIds.get(finalI));
            }};
            when(tagRepository.getById(tag.getId())).thenReturn(tag);
            when(tagRepository.findByName(tags.get(i))).thenReturn(Optional.of(tag));
        }
        List<Tag> resultTags = tagService.getTagsByNames(stringOfTags);
        Assertions.assertEquals(tags.size(), resultTags.size());
    }
}