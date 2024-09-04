package com.i2i.zing.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryRequestDto;
import com.i2i.zing.service.CategoryService;

/**
 * <p>
 * This class is the Controller for Category Operations
 * like Add, Update, Read and Delete the Categories.
 * </p>
 */
@Controller
@RequestMapping("zing/api/v1/dark-stores/categories")
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
    public ResponseEntity<APIResponse> addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        APIResponse apiResponse = categoryService.addCategory(categoryRequestDto);
        if (null == apiResponse.getData()) {
            logger.warn("An Error Occurred while adding Category with Name :{}", categoryRequestDto.getName());
        }
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
        if (null == apiResponse.getData()) {
            logger.warn("An Error Occurred while getting Categories from the Database..");
        }
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
        if (null == apiResponse.getData()) {
            logger.warn("An Error Occurred while getting Category from the Database with Id :{}", categoryId);
        }
        logger.info("Category Retrieved Successfully with Id : {}", categoryId);
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
        if (null != apiResponse.getData()) {
            logger.warn("An Error occurred while Deleting the Category with Id : {}", categoryId);
        }
        logger.info("Category deleted Successfully with Id : {}", categoryId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/{categoryId}/items")
    public ResponseEntity<APIResponse> getItemsByCategoryId(@PathVariable String categoryId) {
        APIResponse apiResponse = categoryService.getItemsByCategoryId(categoryId);
        if (null == apiResponse.getData()) {
            logger.warn("An Error Occurred while getting Items by Category with Id :{}", categoryId);
        }
        logger.info("Items Retrieved By Category..");
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
