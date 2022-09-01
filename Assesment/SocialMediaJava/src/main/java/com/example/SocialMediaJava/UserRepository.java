package com.example.SocialMediaJava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM USER u WHERE u.email= ?1")
    User findByEmail(String email);
}
