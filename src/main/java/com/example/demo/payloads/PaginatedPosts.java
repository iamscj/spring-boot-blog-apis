package com.example.demo.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PaginatedPosts {

    private List<PostDto> content;

    private PaginationInfo paginationInfo;
}
