package com.alok.blogger.blogapp.controllers;

import com.alok.blogger.blogapp.payloads.CommentDto;
import com.alok.blogger.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable int postId) {
        CommentDto comment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable("postId") int postId) {
        List<CommentDto> commentDto = this.commentService.getCommentByPostId(postId);
        return new ResponseEntity<List<CommentDto>>(commentDto, HttpStatus.FOUND);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getById(@PathVariable int commentId) {
        CommentDto commentDto = this.commentService.getCommentById(commentId);
        return new ResponseEntity<CommentDto>(commentDto, HttpStatus.FOUND);
    }
}
