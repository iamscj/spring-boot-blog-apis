package com.example.demo.controllers;

import com.example.demo.entities.Post;
import com.example.demo.payloads.PostDto;
import com.example.demo.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestParam Integer userId,
            @RequestParam Integer categoryId,
            @Valid @RequestBody PostDto postDto
    ) {
        return new ResponseEntity<>(
                this.postService.createPost(postDto, userId, categoryId),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getByUser")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@RequestParam Integer userId) {
        return new ResponseEntity<>(
          this.postService.getPostsByUser(userId),
          HttpStatus.OK
        );
    }

    @GetMapping("/getByCategory")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@RequestParam Integer categoryId) {
        return new ResponseEntity<>(
                this.postService.getPostsByCategory(categoryId),
                HttpStatus.OK
        );
    }
}
