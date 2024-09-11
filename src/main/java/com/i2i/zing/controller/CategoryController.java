package com.i2i.zing.controller;

import jakarta.validation.Valid;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.zing.dto.CategoryCreationDto;
import org.apache.logging.log4j.LogManager;
import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryRequestDto;
import com.i2i.zing.service.CategoryService;

/**
 * <p>
 * This class is the Controller for Category Operations
 * like Add, Update, Read and Delete the Categories.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/darkstores/categories")
public class CategoryController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    CategoryService categoryService;

    /**
     * <p>
     * This method add the Category to the Database
     * like id, name
     * </p>
     *
     * @param categoryRequestDto {@link CategoryRequestDto} Category as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        APIResponse apiResponse = categoryService.addCategory(categoryRequestDto);
        logger.info("Category Created Successfully..");
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get all the Categories from the Database
     * </p>
     *
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping
    public ResponseEntity<APIResponse> getCategories() {
        APIResponse apiResponse = categoryService.getCategories();
        logger.info("Categories Retrieved Successfully..");
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get the Category by ID
     * </p>
     *
     * @param categoryId - To Identify the Category
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable String categoryId) {
        APIResponse apiResponse = categoryService.getCategoryById(categoryId);
        logger.info("Category Retrieved Successfully with Id : {}", categoryId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method update the Category Details like name and Description
     * </p>
     * @param categoryCreationDto {@link CategoryCreationDto} - as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PutMapping
    public ResponseEntity<APIResponse> updateCategory(@RequestBody CategoryCreationDto categoryCreationDto) {
        APIResponse apiResponse = categoryService.updateCategory(categoryCreationDto);
        logger.info("Category Updated Successfully..");
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method delete the Category in the Database
     * </p>
     *
     * @param categoryId - To Identify the Category
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable String categoryId) {
        APIResponse apiResponse = categoryService.deleteCategory(categoryId);
        logger.info("Category deleted Successfully with Id : {}", categoryId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get all the Items by Category
     * </p>
     * @param categoryId - To identify the Category
     * @return APIResponse Details like Status, Data
     */
    @GetMapping("/{categoryId}/items")
    public ResponseEntity<APIResponse> getItemsByCategoryId(@PathVariable String categoryId) {
        APIResponse apiResponse = categoryService.getItemsByCategoryId(categoryId);
        logger.info("Items Retrieved By Category..");
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
