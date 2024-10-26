package com.example.demo.services;

import com.example.demo.entities.Category;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.payloads.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto post, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto post);

    void deletePost(Integer postId);

    List<PostDto> getAllPosts();

    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> getPostsByCategory(Integer categoryId);

    List<PostDto> searchPostsByTitle(String keyword);
}
