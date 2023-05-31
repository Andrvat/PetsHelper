package com.example.petshelper.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Multimedia")
public class Multimedia {
    @Id
    private Long id;
    @ManyToOne
    private Post post;
    @Lob
    private byte[] object;
}
