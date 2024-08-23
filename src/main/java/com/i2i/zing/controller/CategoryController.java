package com.i2i.zing.controller;

import com.i2i.zing.dto.CategoryDto;
import com.i2i.zing.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     This class is the Controller for Category Operations
 *     like Add, Update, Read and Delete the Categories.
 * </p>
 */
@Controller
@RequestMapping("zing/api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * <p>
     *     This method add the Category to the Database
     *     like id, name
     * </p>
     * @param categoryDto {@link CategoryDto} Category as Dto Object
     * @return CategoryDto {@link CategoryDto} Dto Object
     */
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategoryDto = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }

    /**
     * <p>
     *     This method get all the Categories from the Database
     * </p>
     * @return List of Categories as Dto Object {@link CategoryDto}
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    /**
     * <p>
     *     This method get the Category by ID
     * </p>
     * @param categoryId - To Identify the Category
     * @return CategoryDto {@link CategoryDto} - Dto Object
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(String categoryId) {
        CategoryDto retrievedCategory = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(retrievedCategory, HttpStatus.OK);
    }

    /**
     * <p>
     *     This method delete the Category in the Database
     * </p>
     * @param categoryId - To Identify the Category
     * @return ResponseEntity Status OK
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(String categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
