package com.example.demo.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private int id;

    @NotEmpty
    @Size(min = 4, max = 100, message = "Comment must be minimum of 4 characters and maximum of 100 characters")
    private String content;

    private UserDto user;
}
