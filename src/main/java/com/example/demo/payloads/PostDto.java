package com.example.demo.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    @NotEmpty
    @Size(min = 6, max = 20, message = "PostTitle must be minimum of 6 characters and maximum of 30 characters")
    private String title;

    @NotEmpty
    @Size(min = 20, max = 20000, message = "PostContent must be minimum of 20 characters and maximum of 20000 characters")
    private String content;

    private String imageName = "default.png";

    private Date createDate;

    private CategoryDto category;

    private UserDto user;
}
