package com.i2i.zing.mapper;

import com.i2i.zing.dto.CategoryDto;
import com.i2i.zing.model.Cart;
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
     * @return CategoryDto to display.
     */
    public static CategoryDto convertEntityToDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .items(category.getItems())
                .build();
        return categoryDto;
    }

    /**
     * <p>
     *     Converts the dto to entity Object for
     *     Database Operations
     * </p>
     * @param categoryDto {@link CategoryDto} for conversion into entity.
     * @return Category to display.
     */
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
