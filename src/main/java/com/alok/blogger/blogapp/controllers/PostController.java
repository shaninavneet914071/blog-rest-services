package com.alok.blogger.blogapp.controllers;

import com.alok.blogger.blogapp.payloads.PostDto;
import com.alok.blogger.blogapp.payloads.PostResponse;
import com.alok.blogger.blogapp.services.FileService;
import com.alok.blogger.blogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<?> createdPost(@RequestParam(name = "image", defaultValue = "default.png",required = false) MultipartFile image,@RequestParam(name = "post") String postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) throws IOException {
        PostDto createdPost = this.postService.createPost(path, image, postDto, userId, categoryId);
        return new ResponseEntity< >(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> allPostByCategory = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(allPostByCategory, HttpStatus.FOUND);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postById = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postById, HttpStatus.FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> postDtos = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<PostResponse> getAll(@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> getPostByKeyword(@PathVariable String keyword) {
        List<PostDto> posts = this.postService.searchPosts(keyword);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("image/upload/{postId}")
    public ResponseEntity<PostDto> fileUpload(@PathVariable Integer postId, @RequestParam("image") MultipartFile image) throws IOException {
        String fileName = this.fileService.uploadImage(path, image);
        PostDto postDto = this.postService.getPostById(postId);
        postDto.setImageName(fileName);
        PostDto postDto1 = this.postService.update(postDto, postId);
        return new ResponseEntity<PostDto>(postDto1, HttpStatus.OK);
    }

    @GetMapping(value = "image/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
