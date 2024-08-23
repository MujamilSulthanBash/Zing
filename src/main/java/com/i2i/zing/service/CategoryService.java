package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *     This interface has Abstract methods to implements the
 *     methods for Category Operations like add, retrieve and
 *     delete
 * </p>
 */
@Service
public interface CategoryService {

    /**
     * <p>
     *     This method add the Category Record to the Database
     * </p>
     * @param categoryDto {@link CategoryDto} - Category as Dto Object
     * @return CategoryDto as Dto Object
     */
    APIResponse addCategory(CategoryDto categoryDto);

    /**
     * <p>
     *     This method retrieve all the Categories in the Database
     * </p>
     * @return List of CategoryDtos {@link CategoryDto}
     */
    APIResponse getCategories();

    /**
     * <p>
     *     This method retrieve specific Category by ID
     * </p>
     * @param categoryId - To Identify the Category
     * @return CategoryDto {@link CategoryDto} - as Dto Object
     */
    APIResponse getCategoryById(String categoryId);

    /**
     * <p>
     *     This method delete the Category in the Database table
     * </p>
     * @param categoryId - To Identify the Category
     */
    APIResponse deleteCategory(String categoryId);
}
