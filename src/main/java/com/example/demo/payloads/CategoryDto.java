package com.example.demo.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;

    @NotEmpty
    @Size(min = 3, max = 20, message = "CategoryTitle must be minimum of 4 characters and maximum of 20 characters")
    private String categoryTitle;

    @NotEmpty
    private String categoryDescription;
}
