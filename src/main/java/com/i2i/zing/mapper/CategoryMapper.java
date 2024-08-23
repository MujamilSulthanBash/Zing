package com.i2i.zing.mapper;

import com.i2i.zing.dto.CategoryDto;
import com.i2i.zing.model.Category;

public class CategoryMapper {
    public static CategoryDto convertEntityToDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .items(category.getItems())
                .build();
        return categoryDto;
    }

    public static Category convertDtoToEntity(CategoryDto categoryDto) {
        Category category = Category.builder()
                .categoryId(categoryDto.getCategoryId())
                .description(categoryDto.getDescription())
                .items(categoryDto.getItems())
                .name(categoryDto.getName())
                .build();
        return category;
    }
}
