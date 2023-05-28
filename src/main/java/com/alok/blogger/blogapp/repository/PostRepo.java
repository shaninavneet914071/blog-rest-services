package com.alok.blogger.blogapp.repository;

import com.alok.blogger.blogapp.entities.Category;
import com.alok.blogger.blogapp.entities.Post;
import com.alok.blogger.blogapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

}
