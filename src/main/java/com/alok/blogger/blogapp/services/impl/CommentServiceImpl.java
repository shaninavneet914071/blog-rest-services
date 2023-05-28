package com.alok.blogger.blogapp.services.impl;

import com.alok.blogger.blogapp.entities.Comment;
import com.alok.blogger.blogapp.entities.Post;
import com.alok.blogger.blogapp.exceptions.ResourceNotFoundException;
import com.alok.blogger.blogapp.payloads.CommentDto;
import com.alok.blogger.blogapp.repository.CategoryRepo;
import com.alok.blogger.blogapp.repository.CommentRepo;
import com.alok.blogger.blogapp.repository.PostRepo;
import com.alok.blogger.blogapp.repository.UserRepo;
import com.alok.blogger.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    /**
     * @param commentDto
     * @param postId
     * @param categoryId
     * @param userId
     * @return
     */
    @Autowired
    PostRepo postRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment comment1 = this.commentRepo.save(comment);
        return this.modelMapper.map(comment1, CommentDto.class);
    }

    /**
     * @param postId
     * @return
     */
    @Override
    public List<CommentDto> getCommentByPostId(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        List<Comment> comments = this.commentRepo.findByPost(post);
        List<CommentDto> commentDtos = comments.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
        return commentDtos;
    }

    public CommentDto getCommentById(int commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentID", commentId));
        CommentDto commentDtos = this.modelMapper.map(comment, CommentDto.class);
        return commentDtos;

    }

}
