package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryCreationDto;
import com.i2i.zing.dto.CategoryRequestDto;
import com.i2i.zing.dto.CategoryResponseDto;
import com.i2i.zing.dto.ItemUpdateDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.mapper.CategoryMapper;
import com.i2i.zing.mapper.ItemMapper;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;
import com.i2i.zing.repository.CategoryRepository;
import com.i2i.zing.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    CategoryRepository categoryRepository;

    public APIResponse addCategory(CategoryRequestDto categoryRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Category category = CategoryMapper.convertDtoToCreationEntity(categoryRequestDto);
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            logger.warn("Category Already Exists.. :");
            throw new EntityNotFoundException("Category Already Exists with Name : " + categoryRequestDto.getName());
        }
        CategoryCreationDto categoryCreationDto = CategoryMapper.convertEntityToCreationDto(categoryRepository.save(category));
        logger.info("Category Added Successfully..");
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
        if (categories.isEmpty()) {
            logger.warn("Categories List is Empty..");
        }
        return apiResponse;
    }

    @Override
    public APIResponse getCategoryById(String categoryId) {
        APIResponse apiResponse = new APIResponse();
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        if (null == category) {
            logger.warn("Category not found with Id : {} not found.", categoryId);
            throw new EntityNotFoundException("Category Not found with Id : " + categoryId);
        }
        CategoryResponseDto categoryResponseDto = CategoryMapper.convertEntityToResponseDto(category);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(categoryResponseDto);
        return apiResponse;
    }

    @Override
    public APIResponse deleteCategory(String categoryId) {
        APIResponse apiResponse = new APIResponse();
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        if (null == category) {
            logger.warn("Category Not found with Id : {}", categoryId);
            throw new EntityNotFoundException("Category Not found to delete with Id : " + categoryId);
        }
        category.setDeleted(true);
        categoryRepository.save(category);
        apiResponse.setData("Category Deleted Successfully : " + categoryId);
        apiResponse.setStatus(HttpStatus.OK.value());
        logger.info("Category Deleted Successfully : {}", categoryId);
        return apiResponse;
    }

    @Override
    public APIResponse getItemsByCategoryId(String categoryId) {
        APIResponse apiResponse = new APIResponse();
        Category category = categoryRepository.findByIsDeletedFalseAndCategoryId(categoryId);
        List<ItemUpdateDto> itemUpdateDtos = new ArrayList<>();
        for (Item item : category.getItems()) {
            itemUpdateDtos.add(ItemMapper.convertEntityToResponseDto(item));
        }
        apiResponse.setData(itemUpdateDtos);
        apiResponse.setStatus(HttpStatus.OK.value());
        if (itemUpdateDtos.isEmpty()) {
            logger.warn("Items List is Empty..");
            throw new EntityNotFoundException("Category Not found with Id : " + categoryId);
        }
        return apiResponse;
    }

}
