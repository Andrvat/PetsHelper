package com.example.petshelper.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.TextType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private UserPersonality author;

    @ManyToOne
    private Post post;

    private String text;

    private Long parentId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Comment() {
    }

    public Comment(UserPersonality personality, Post post, String text, Date date,Long parentId) {
        this.author = personality;
        this.post = post;
        this.text = text;
        this.date = date;
        this.parentId = parentId;
    }
}
