package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.*;
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

    public APIResponse addCategory(CategoryRequestDto categoryRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Category category = CategoryMapper.convertDtoToCreationEntity(categoryRequestDto);
        CategoryCreationDto categoryCreationDto = CategoryMapper.convertEntityToCreationDto(categoryRepository.save(category));
        apiResponse.setData(categoryCreationDto);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCategories() {
        APIResponse apiResponse = new APIResponse();
        List<CategoryResponseDto> result = new ArrayList<>();
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        for (Category category : categories) {
            result.add(CategoryMapper.convertEntityToResponseDto(category));
        }
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCategoryById(String categoryId) {
        APIResponse apiResponse = new APIResponse();
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        CategoryResponseDto categoryResponseDto = CategoryMapper.convertEntityToResponseDto(category);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(categoryResponseDto);
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
        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
        for (Item item : category.getItems()) {
            itemResponseDtos.add(ItemMapper.convertEntityToResponseDto(item));
        }
        apiResponse.setData(itemResponseDtos);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
