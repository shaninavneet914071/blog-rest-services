package com.alok.blogger.blogapp.services;

import com.alok.blogger.blogapp.payloads.PostDto;
import com.alok.blogger.blogapp.payloads.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
//    PostDto createPost(String postDto, Integer userId, Integer categoryId);

    PostDto update(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByCategory(Integer id);

    List<PostDto> getPostsByUser(Integer id);

    List<PostDto> searchPosts(String keyword);

    PostDto createPost(String path, MultipartFile image, String postDto, Integer userId, Integer categoryId) throws IOException;
}
