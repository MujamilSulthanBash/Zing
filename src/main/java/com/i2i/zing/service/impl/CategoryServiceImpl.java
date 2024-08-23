package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.i2i.zing.dto.CategoryDto;
import com.i2i.zing.mapper.CategoryMapper;
import com.i2i.zing.model.Category;

import com.i2i.zing.repository.CategoryRepository;
import com.i2i.zing.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.convertDtoToEntity(categoryDto);
        return CategoryMapper.convertEntityToDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<CategoryDto> result = new ArrayList<>();
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        for (Category category : categories) {
            result.add(CategoryMapper.convertEntityToDto(category));
        }
        return result;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        return CategoryMapper.convertEntityToDto(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        category.setDeleted(true);
        categoryRepository.save(category);
    }
}
