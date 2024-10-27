package com.example.demo.services;

import com.example.demo.payloads.PaginatedPosts;
import com.example.demo.payloads.PostDto;

public interface PostService {

    PostDto createPost(PostDto post, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto post);

    void deletePost(Integer postId);

    PaginatedPosts getAllPosts(Integer PageNumber, Integer PageSize, String sortBy, String sortDir);

    PostDto getPostById(Integer postId);

    PaginatedPosts getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PaginatedPosts getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PaginatedPosts searchPosts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
