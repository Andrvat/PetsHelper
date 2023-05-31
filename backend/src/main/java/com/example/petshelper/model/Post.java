package com.example.petshelper.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import org.hibernate.type.BlobType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String header;

    @ManyToOne
    private UserPersonality author;

    @ManyToOne
    private Category category;

    private String city;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Boolean isActual;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Post_Tag",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )

    private List<Tag> tags;

    @OneToMany(mappedBy = "post")
    private List<Image> images;

    private Boolean isModerationPassed;
}
