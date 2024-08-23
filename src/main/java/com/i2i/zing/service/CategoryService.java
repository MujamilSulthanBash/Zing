package com.i2i.zing.service;

import com.i2i.zing.dto.CategoryDto;

import java.util.List;

/**
 * <p>
 *     This interface has Abstract methods to implements the
 *     methods for Category Operations like add, retrieve and
 *     delete
 * </p>
 */
public interface CategoryService {

    /**
     * <p>
     *     This method add the Category Record to the Database
     * </p>
     * @param categoryDto {@link CategoryDto} - Category as Dto Object
     * @return CategoryDto as Dto Object
     */
    CategoryDto addCategory(CategoryDto categoryDto);

    /**
     * <p>
     *     This method retrieve all the Categories in the Database
     * </p>
     * @return List of CategoryDtos {@link CategoryDto}
     */
    List<CategoryDto> getCategories();

    /**
     * <p>
     *     This method retrieve specific Category by ID
     * </p>
     * @param categoryId - To Identify the Category
     * @return CategoryDto {@link CategoryDto} - as Dto Object
     */
    CategoryDto getCategoryById(String categoryId);

    /**
     * <p>
     *     This method delete the Category in the Database table
     * </p>
     * @param categoryId - To Identify the Category
     */
    void deleteCategory(String categoryId);
}
