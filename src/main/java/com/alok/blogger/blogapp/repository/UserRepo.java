package com.alok.blogger.blogapp.repository;

import com.alok.blogger.blogapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
