package com.example.petshelper.service;

import com.example.petshelper.exceptions.SectionAlreadyExistsException;
import com.example.petshelper.model.Section;
import com.example.petshelper.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;


    public void saveSection(Section section) throws SectionAlreadyExistsException {
        Section sectionInDB = sectionRepository.findByName(section.getName());
        if(sectionInDB != null) throw new SectionAlreadyExistsException("Section " + section.getName() + " already exitts.");
        sectionRepository.save(section);
    }

    public Section getSection(Long id) {
        Optional<Section> section = sectionRepository.findById(id);
        return section.orElse(null);

    }
}
