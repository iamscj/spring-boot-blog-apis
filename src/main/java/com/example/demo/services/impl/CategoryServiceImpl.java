package com.example.demo.services.impl;

import com.example.demo.entities.Category;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.CategoryDto;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);

        return categoryToDto(this.categoryRepo.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto category) {
        int categoryId = category.getCategoryId();

        Category categoryToUpdate = this.categoryRepo
                                        .findById(categoryId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryToUpdate.setCategoryTitle(category.getCategoryTitle());
        categoryToUpdate.setCategoryDescription(category.getCategoryDescription());

        Category updatedCategory = this.categoryRepo.save(categoryToUpdate);

        return this.categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(CategoryDto category) {
        int categoryId = category.getCategoryId();

        Category deleteCategory = this.categoryRepo.findById(category.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        this.categoryRepo.delete(deleteCategory);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();

        return categories.stream()
                .map(this::categoryToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepo
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return this.categoryToDto(category);
    }

    private Category dtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto, Category.class);
    }

    private CategoryDto categoryToDto(Category category) {
        return this.modelMapper.map(category, CategoryDto.class);
    }
}
