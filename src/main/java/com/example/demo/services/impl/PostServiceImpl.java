package com.example.demo.services.impl;

import com.example.demo.entities.Category;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.PaginatedPosts;
import com.example.demo.payloads.PaginationInfo;
import com.example.demo.payloads.PostDto;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.repositories.PostRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = this.categoryRepo
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Post post = this.dtoToPost(postDto);
        post.setCreateDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = this.postRepo.save(post);

        return postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        int postId = postDto.getPostId();
        Post post = this.postRepo
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        this.postRepo.save(post);

        return postToDto(post);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        this.postRepo.delete(post);
    }

    @Override
    public PaginatedPosts getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Page<Post> posts = this.postRepo.findAll(pageable);

        return this.getPaginatedPosts(posts);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        return postToDto(post);
    }

    @Override
    public PaginatedPosts getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        User user = this.userRepo
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Page<Post> posts = this.postRepo.findByUser(user, pageable);

        return this.getPaginatedPosts(posts);
    }

    @Override
    public PaginatedPosts getPostsByCategory(
            Integer categoryId,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir
    ) {
        Category category = this.categoryRepo
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Page<Post> posts = this.postRepo.findByCategory(category, pageable);

        return this.getPaginatedPosts(posts);
    }

    @Override
    public PaginatedPosts searchPosts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Page<Post> posts = this.postRepo.findByTitleContaining(keyword, pageable);

        return this.getPaginatedPosts(posts);
    }

    public Post dtoToPost(PostDto postDto) {
        return this.modelMapper.map(postDto, Post.class);
    }

    public PostDto postToDto(Post post) {
        return this.modelMapper.map(post, PostDto.class);
    }

    public PaginatedPosts getPaginatedPosts(Page<Post> posts) {
        List<PostDto> postDtos = posts
                .stream()
                .map(this::postToDto)
                .toList();

        PaginatedPosts paginatedPosts = new PaginatedPosts();
        PaginationInfo paginationInfo = new PaginationInfo(
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );

        paginatedPosts.setContent(postDtos);
        paginatedPosts.setPaginationInfo(paginationInfo);

        return paginatedPosts;
    }
}
