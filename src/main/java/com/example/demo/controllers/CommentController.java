package com.example.demo.controllers;

import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.CommentDto;
import com.example.demo.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentDto commentDto,
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "postId") Integer postId
    ) {
        return new ResponseEntity<>(
                this.commentService.createComment(commentDto, postId, userId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteComment(@RequestParam Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(
                new ApiResponse("Comment deleted successfully", true),
                HttpStatus.OK
        );
    }
}
