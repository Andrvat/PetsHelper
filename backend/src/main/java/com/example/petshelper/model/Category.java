package com.example.petshelper.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Category")
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String russianName;
    @ManyToOne
    private Section section;
}
