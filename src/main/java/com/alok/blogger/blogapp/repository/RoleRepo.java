package com.alok.blogger.blogapp.repository;

import com.alok.blogger.blogapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
