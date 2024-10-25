package com.example.demo.services;

import com.example.demo.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto category);

    CategoryDto updateCategory(CategoryDto category);

    void deleteCategory(CategoryDto category);

    List<CategoryDto> getCategories();

    CategoryDto getCategory(Integer categoryId);
}
