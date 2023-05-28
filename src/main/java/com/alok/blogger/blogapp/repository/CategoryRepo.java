package com.alok.blogger.blogapp.repository;

import com.alok.blogger.blogapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
