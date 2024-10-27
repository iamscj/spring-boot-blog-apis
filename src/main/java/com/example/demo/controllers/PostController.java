package com.example.demo.controllers;

import com.example.demo.config.AppConstants;
import com.example.demo.exceptions.FileFormatException;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.PaginatedPosts;
import com.example.demo.payloads.PostDto;
import com.example.demo.services.FileService;
import com.example.demo.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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
    public ResponseEntity<PaginatedPosts> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.POST_SORT_DIR, required = false) String sortDir
    ) {
        return new ResponseEntity<>(this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        return new ResponseEntity<>(this.postService.getPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/getByUser")
    public ResponseEntity<PaginatedPosts> getPostsByUserId(
            @RequestParam Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.POST_SORT_DIR, required = false) String sortDir
    ) {
        return new ResponseEntity<>(
          this.postService.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortDir),
          HttpStatus.OK
        );
    }

    @GetMapping("/getByCategory")
    public ResponseEntity<PaginatedPosts> getPostsByCategoryId(
            @RequestParam Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.POST_SORT_DIR, required = false) String sortDir
    ) {
        return new ResponseEntity<>(
                this.postService.getPostsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir),
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

    @GetMapping("/search")
    public ResponseEntity<PaginatedPosts> searchPost(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.POST_SORT_DIR, required = false) String sortDir
    ) {
        return new ResponseEntity<>(this.postService.searchPosts(title, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam Integer postId
    ) throws IOException, FileFormatException {
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        this.postService.updatePost(postDto);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }
}
