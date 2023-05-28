package com.alok.blogger.blogapp.services;

import com.alok.blogger.blogapp.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, int postId);

    List<CommentDto> getCommentByPostId(int userId);

    CommentDto getCommentById(int commentId);
}
