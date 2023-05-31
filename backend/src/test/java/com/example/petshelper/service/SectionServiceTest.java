package com.example.petshelper.service;

import com.example.petshelper.exceptions.SectionAlreadyExistsException;
import com.example.petshelper.model.Section;
import com.example.petshelper.repository.SectionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SectionServiceTest {

    @Autowired
    private SectionService sectionService;

    @MockBean
    private SectionRepository sectionRepository;

    @Test
    public void testContextLoading() {
        Assertions.assertNotNull(sectionService);
    }

    @Test
    public void testSaveAlreadyExistSection() {
        when(sectionRepository.findByName(any())).thenReturn(new Section());
        Assertions.assertThrows(SectionAlreadyExistsException.class, () -> sectionService.saveSection(new Section()));
    }

    @Test
    public void testSaveSection() {
        when(sectionRepository.findByName(any())).thenReturn(null);
        when(sectionRepository.save(any())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> sectionService.saveSection(new Section()));
    }

    @Test
    public void testGetSection() {
        Section section = new Section();
        section.setId(1L);
        section.setName("abc");
        when(sectionRepository.findById(section.getId())).thenReturn(Optional.of(section));
        Section resultSection = sectionService.getSection(section.getId());
        Assertions.assertEquals(section, resultSection);
    }
}