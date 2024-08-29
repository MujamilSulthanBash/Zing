package com.i2i.zing.mapper;

import com.i2i.zing.dto.CategoryCreationDto;
import com.i2i.zing.dto.CategoryRequestDto;
import com.i2i.zing.dto.CategoryResponseDto;
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
     *     This method convert the Entity to Response Dto
     * </p>
     * @param category - Entity Object
     * @return CategoryResponseDto - Response as Dto Object
     */
    public static CategoryResponseDto convertEntityToResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .items(category.getItems())
                .build();
    }

    /**
     * <p>
     *     This method convert the Entity to Creation Dto
     * </p>
     * @param category - Category as Entity Object
     * @return - CategoryCreationDto as Dto Object
     */
    public static CategoryCreationDto convertEntityToCreationDto(Category category) {
        return CategoryCreationDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    /**
     * <p>
     *     This method convert the Request Dto to Entity Object
     * </p>
     * @param categoryRequestDto {@link CategoryRequestDto} - Category Details as Dto Object
     * @return Category as Entity Object
     */
    public static Category convertDtoToCreationEntity(CategoryRequestDto categoryRequestDto) {
        Category category = Category.builder()
                .name(categoryRequestDto.getName())
                .description(categoryRequestDto.getDescription())
                .build();
        return category;
    }
}
