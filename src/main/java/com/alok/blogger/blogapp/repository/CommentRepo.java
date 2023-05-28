package com.alok.blogger.blogapp.repository;

import com.alok.blogger.blogapp.entities.Comment;
import com.alok.blogger.blogapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
}
