package com.i2i.zing.service;

import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryRequestDto;
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
     * @param categoryRequestDto {@link CategoryRequestDto} - Category as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    APIResponse addCategory(CategoryRequestDto categoryRequestDto);

    /**
     * <p>
     *     This method retrieve all the Categories in the Database
     * </p>
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getCategories();

    /**
     * <p>
     *     This method retrieve specific Category by ID
     * </p>
     * @param categoryId - To Identify the Category
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getCategoryById(String categoryId);

    /**
     * <p>
     *     This method delete the Category in the Database table
     * </p>
     * @param categoryId - To Identify the Category
     * @return APIResponse Details like Status, Data.
     */
    APIResponse deleteCategory(String categoryId);

    /**
     * <p>
     *     This method retrieve the item records by Category ID
     * </p>
     * @param categoryId - To Identify the Category
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getItemsByCategoryId(String categoryId);
}
