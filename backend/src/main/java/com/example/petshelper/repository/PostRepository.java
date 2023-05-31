package com.example.petshelper.repository;

import com.example.petshelper.model.Category;
import com.example.petshelper.model.Post;
import com.example.petshelper.model.UserPersonality;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByHeader(String header);

    List<Post> findByIsModerationPassedOrderByDate(Boolean isModerationPassed);

    List<Post> findByCategoryOrderByDate(Category category);

    List<Post> findAllByHeaderContains(String headerPart);

    List<Post> findByAuthor(UserPersonality personality);

    Long countByAuthor(UserPersonality personality);

    @Query("select count(p), p from Tag t join t.posts p where t.id in :tagIds and p.isModerationPassed = true group by p order by count(p)")
    List<Object[]> findAllByTagsOrdered(List<Long> tagIds);


//    @Query("select p from Tag t join t.posts p where t.id in :tagIds")
//    List<Post> findAllByTagsOrdered(List<Long> tagIds);
    @Query("select p from UserPersonality me join me.subscription subscriptions join subscriptions.posts p " +
            "where me.id = :myId and p.isModerationPassed = true")
    List<Post> getSubscriptionsPosts(Long myId);

}
