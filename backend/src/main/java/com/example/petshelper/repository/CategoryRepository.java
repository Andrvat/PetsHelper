package com.example.petshelper.repository;

import com.example.petshelper.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    Category findByRussianName(String name);
}
