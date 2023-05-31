package com.example.petshelper.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_personality")
public class UserPersonality {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "credential_id")
    private UserCredential credential;
    private String firstName;
    private String lastName;
    private String city;
    private String phoneNumber;
    private int age;
    private String aboutMe;
    private String gender;
    private int rating;

    @ManyToMany
    @JsonIgnore
    private List<UserPersonality> subscription = new LinkedList<>();

    @ManyToMany(mappedBy = "subscription")
    @JsonIgnore
    private List<UserPersonality> subscribers = new LinkedList<>();

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Post> posts;

    public UserPersonality() {

    }

    public UserPersonality(UserCredential credential) {
        this.credential = credential;
        this.rating = 0;
    }
}
