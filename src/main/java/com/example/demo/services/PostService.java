package com.example.demo.services;

import com.example.demo.payloads.PaginatedPosts;
import com.example.demo.payloads.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto post, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto post);

    void deletePost(Integer postId);

    PaginatedPosts getAllPosts(Integer PageNumber, Integer PageSize, String sortBy, String sortDir);

    PostDto getPostById(Integer postId);

    PaginatedPosts getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);

    PaginatedPosts getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

    List<PostDto> searchPostsByTitle(String keyword);
}
