package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.i2i.zing.model.Category;

/**
 * <p>
 *     This class has Abstract methods for Category
 *     Operations like get all the Categories and Category Object
 * </p>
 */
public interface CategoryRepository extends JpaRepository<Category, String> {
    /**
     * <p>
     *     This method get all the Categories List
     * </p>
     * @return List of Categories
     */
    List<Category> findByIsDeletedFalse();

    /**
     * <p>
     *     This method get the Category Object by
     *     Category Id
     * </p>
     * @param categoryId - To Identify the Category
     * @return Category as Entity Object
     */
    Category findByIsDeletedFalseAndCategoryId(String categoryId);
}
