package com.example.petshelper.repository;

import com.example.petshelper.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAll();

    @Query("select t from Tag t where t.name like %:namePart%")
    List<Tag> findAllByNameContains(String namePart);

    Optional<Tag> findByName(String value);
}
