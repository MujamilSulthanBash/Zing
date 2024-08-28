package com.i2i.zing.mapper;

import com.i2i.zing.dto.CategoryRequestDto;
import com.i2i.zing.model.Category;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class CategoryMapper {
    /**
     * <p>
     *     Converts the entity to displayable dto
     * </p>
     * @param category {@link Category} for conversion into dto.
     * @return CategoryRequestDto to display.
     */
    public static CategoryRequestDto convertEntityToDto(Category category) {
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .items(category.getItems())
                .build();
        return categoryRequestDto;
    }

    /**
     * <p>
     *     Converts the dto to entity Object for
     *     Database Operations
     * </p>
     * @param categoryRequestDto {@link CategoryRequestDto} for conversion into entity.
     * @return Category to display.
     */
    public static Category convertDtoToEntity(CategoryRequestDto categoryRequestDto) {
        Category category = Category.builder()
                .categoryId(categoryRequestDto.getCategoryId())
                .description(categoryRequestDto.getDescription())
                .items(categoryRequestDto.getItems())
                .name(categoryRequestDto.getName())
                .build();
        return category;
    }
}
