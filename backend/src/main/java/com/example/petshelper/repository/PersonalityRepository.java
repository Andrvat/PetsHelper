package com.example.petshelper.repository;

import com.example.petshelper.model.UserCredential;
import com.example.petshelper.model.UserPersonality;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PersonalityRepository extends JpaRepository<UserPersonality, Long> {
    UserPersonality findUserPersonalitiesByCredential(UserCredential credential);

//    @Query("select subscriptions from UserPersonality subscriptions where subscriptions.subscribers contains :subscriber")
//    List<UserPersonality> f(UserPersonality subscriber);

    List<UserPersonality> findAllBySubscribersContains(UserPersonality subscriber);

    List<UserPersonality> findAllBySubscriptionContains(UserPersonality subscription);
}
