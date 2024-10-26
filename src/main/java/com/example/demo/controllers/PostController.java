package com.example.demo.controllers;

import com.example.demo.entities.Post;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.PostDto;
import com.example.demo.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return new ResponseEntity<>(this.postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        return new ResponseEntity<>(this.postService.getPostById(postId), HttpStatus.OK);
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

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(this.postService.updatePost(postDto), HttpStatus.OK);
    }

}
