package com.example.petshelper.repository;

import com.example.petshelper.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section,Long> {

    Optional<Section> findById(Long id);

    Section findByName(String name);
}
