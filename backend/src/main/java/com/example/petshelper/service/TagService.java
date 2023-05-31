package com.example.petshelper.service;

import com.example.petshelper.model.Tag;
import com.example.petshelper.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Long createTag(String value) {
        Optional<Tag> tagOpt = tagRepository.findByName(value);
        if(tagOpt.isPresent()) {
            return tagOpt.get().getId();
        }
        Tag tag = new Tag(value);
        tag = tagRepository.save(tag);
        return tag.getId();
    }

    public List<Tag> getTagByPart(String part) {
        return tagRepository.findAllByNameContains(part);
    }

    public List<Tag> getTags(List<Long> ids) {
        return tagRepository.findAllById(ids);
    }

    public List<Tag> getTagsByNames(String stringOfTags) {
        List<String> tagsNames = parseTags(stringOfTags);
        List<Tag> tags = new ArrayList<>();

        for(String name: tagsNames) {
            Optional<Tag> tag = getTagByName(name);
            tag.ifPresent(tags::add);
            if(tag.isEmpty()) {
                tags.add(tagRepository.getById(createTag(name)));
            }
        }

        return tags;
    }

    public Optional<Tag> getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    private List<String> parseTags(String stringOfTags) {
        Matcher matcher = Pattern.compile("(#[^#\\s]*)")
                .matcher(stringOfTags);

        List<String> tags = new ArrayList<>();
        while (matcher.find()) {
            tags.add(matcher.group());
        }
        System.out.println(tags);
        return tags;
    }
}
