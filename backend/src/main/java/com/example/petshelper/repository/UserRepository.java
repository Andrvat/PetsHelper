package com.example.petshelper.repository;

import com.example.petshelper.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserCredential, Long> {
    UserCredential findByEmail(String email);
    UserCredential findByUsername(String username);
}
