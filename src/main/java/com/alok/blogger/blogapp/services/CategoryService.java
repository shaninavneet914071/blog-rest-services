package com.alok.blogger.blogapp.services;

import com.alok.blogger.blogapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer id);

    void deleteCategory(Integer id);

    CategoryDto getCategory(Integer id);

    List<CategoryDto> getAllCategory();

}
