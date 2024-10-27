package com.example.demo.services.impl;

import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.CommentDto;
import com.example.demo.repositories.CommentRepo;
import com.example.demo.repositories.PostRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = this.userRepo
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Comment comment = this.dtoToComment(commentDto);

        comment.setPost(post);
        comment.setUser(user);

        return commentToDto(this.commentRepo.save(comment));
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        this.commentRepo.delete(comment);
    }

    private CommentDto commentToDto(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }

    private Comment dtoToComment(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }
}
