package com.alok.blogger.blogapp.services.impl;

import com.alok.blogger.blogapp.entities.Category;
import com.alok.blogger.blogapp.exceptions.ResourceNotFoundException;
import com.alok.blogger.blogapp.payloads.CategoryDto;
import com.alok.blogger.blogapp.repository.CategoryRepo;
import com.alok.blogger.blogapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;

    /**
     * @param categoryDto
     * @return
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }

    /**
     * @param categoryDto
     * @param id
     * @return
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
        Category cat = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", id));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    /**
     * @param id
     */
    @Override
    public void deleteCategory(Integer id) {
        Category cat = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", id));
        this.categoryRepo.delete(cat);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CategoryDto getCategory(Integer id) {
        Category cat = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", id));
        return null;
    }

    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> catDtos = categories.stream()
                .map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return catDtos;
    }
}
