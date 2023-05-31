package com.example.petshelper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Lob
    private byte[] imageData;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="post_id")
    Post post;

    public Image(String image) {
        this.imageData = image.getBytes();
    }

    public Image() {
    }
}
