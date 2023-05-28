package com.alok.blogger.blogapp.services.impl;

import com.alok.blogger.blogapp.entities.Category;
import com.alok.blogger.blogapp.entities.Post;
import com.alok.blogger.blogapp.entities.User;
import com.alok.blogger.blogapp.exceptions.ResourceNotFoundException;
import com.alok.blogger.blogapp.payloads.PostDto;
import com.alok.blogger.blogapp.payloads.PostResponse;
import com.alok.blogger.blogapp.repository.CategoryRepo;
import com.alok.blogger.blogapp.repository.PostRepo;
import com.alok.blogger.blogapp.repository.UserRepo;
import com.alok.blogger.blogapp.services.FileService;
import com.alok.blogger.blogapp.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ObjectMapper mapper;
@Autowired
private FileService fileService;
    /**
     * @param postDto
     * @return
     */
//    @Override
//    public PostDto createPost(String postDto, Integer userId, Integer categoryId) {
//
//        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "usrid", userId));
//        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryId));
//
//        Post post = this.modelMapper.map(postDto, Post.class);
//        post.setImageName("default.png");
//        post.setAddedDate(new Date());
//        post.setUser(user);
//        post.setCategory(category);
//
//        Post newPost = this.postRepo.save(post);
//        return this.modelMapper.map(newPost, PostDto.class);
//    }
    /**
     * @param path
     * @param image
     * @param postDto
     * @param userId
     * @param categoryId
     * @return
     */
    @Override
    public PostDto createPost(String path, MultipartFile image, String postDto, Integer userId, Integer categoryId) throws IOException {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "usrid", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryId));

        String file=this.fileService.uploadImage(path, image);
            PostDto postDto1=this.mapper.readValue(postDto,PostDto.class);
        Post post = this.modelMapper.map(postDto1, Post.class);
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
            post.setImageName(file);

        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    /**
     * @param postDto
     * @param postId
     * @return
     */
    @Override
    public PostDto update(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postID", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);

        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    /**
     * @param postId
     */
    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        this.postRepo.delete(post);
    }

    /**
     * @return
     */
    @Override
    public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePosts = this.postRepo.findAll(pageable);
        List<Post> allPosts = pagePosts.getContent();
        List<PostDto> postDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    /**
     * @param postId
     * @return
     */
    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    /**
     * @param categoryId
     * @return
     */
    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

//    /**
//     * @param keyword
//     * @return
//     */
//    @Override
//    public List<PostDto> searchPosts(String keyword) {
//        List<Post> posts=this.postRepo.findByTitleContaining(keyword);
//        List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
//        return postDtos;
//    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }



}
