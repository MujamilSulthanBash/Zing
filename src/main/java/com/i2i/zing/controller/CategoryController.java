package com.i2i.zing.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryDto;
import com.i2i.zing.service.CategoryService;

/**
 * <p>
 *     This class is the Controller for Category Operations
 *     like Add, Update, Read and Delete the Categories.
 * </p>
 */
@Controller
@RequestMapping("zing/api/v1/categories")
public class CategoryController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    CategoryService categoryService;

    /**
     * <p>
     *     This method add the Category to the Database
     *     like id, name
     * </p>
     * @param categoryDto {@link CategoryDto} Category as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addCategory(@RequestBody CategoryDto categoryDto) {
        logger.debug("Category Adding..");
        APIResponse apiResponse = categoryService.addCategory(categoryDto);
        if (null == apiResponse.getData()) {
            logger.warn("Error Occurred while Adding DarkStore..");
        } else {
            logger.info("Category Added Successfully..");
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get all the Categories from the Database
     * </p>
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping
    public ResponseEntity<APIResponse> getCategories() {
        APIResponse apiResponse = categoryService.getCategories();
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get the Category by ID
     * </p>
     * @param categoryId - To Identify the Category
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<APIResponse> getCategoryById(String categoryId) {
        APIResponse apiResponse = categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method delete the Category in the Database
     * </p>
     * @param categoryId - To Identify the Category
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategory(String categoryId) {
        APIResponse apiResponse = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/categoryId/items")
    public ResponseEntity<APIResponse> getItemsByCategoryId(String categoryId) {
        APIResponse apiResponse = categoryService.getItemsByCategoryId(categoryId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}
