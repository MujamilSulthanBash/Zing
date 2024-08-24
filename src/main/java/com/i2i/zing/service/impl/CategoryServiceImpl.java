package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryDto;
import com.i2i.zing.dto.ItemDto;
import com.i2i.zing.mapper.CategoryMapper;
import com.i2i.zing.mapper.ItemMapper;
import com.i2i.zing.model.Category;

import com.i2i.zing.model.Item;
import com.i2i.zing.repository.CategoryRepository;
import com.i2i.zing.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public APIResponse addCategory(CategoryDto categoryDto) {
        APIResponse apiResponse = new APIResponse();
        Category category = CategoryMapper.convertDtoToEntity(categoryDto);
        CategoryMapper.convertEntityToDto(categoryRepository.save(category));
        apiResponse.setData(category);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCategories() {
        APIResponse apiResponse = new APIResponse();
        List<CategoryDto> result = new ArrayList<>();
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        for (Category category : categories) {
            result.add(CategoryMapper.convertEntityToDto(category));
        }
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCategoryById(String categoryId) {
        APIResponse apiResponse = new APIResponse();
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        CategoryMapper.convertEntityToDto(category);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(category);
        return apiResponse;
    }

    @Override
    public APIResponse deleteCategory(String categoryId) {
        APIResponse apiResponse = new APIResponse();
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        category.setDeleted(true);
        categoryRepository.save(category);
        apiResponse.setData(category);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getItemsByCategoryId(String categoryId) {
        APIResponse apiResponse = new APIResponse();
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : category.getItems()) {
            itemDtos.add(ItemMapper.convertEntityToDto(item));
        }
        apiResponse.setData(itemDtos);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
