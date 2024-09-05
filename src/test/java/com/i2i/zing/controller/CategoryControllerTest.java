package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryRequestDto;
import com.i2i.zing.dto.CategoryResponseDto;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;
import com.i2i.zing.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @Mock
    CategoryServiceImpl categoryService;

    @InjectMocks
    CategoryController categoryController;

    CategoryRequestDto categoryRequestDto;
    CategoryResponseDto categoryResponseDto;
    List<CategoryResponseDto> categories = new ArrayList<>();
    Item item;
    List<Item> items = new ArrayList<>();
    Category category;
    APIResponse addCategoryResponse;
    APIResponse getCategoriesResponse;
    APIResponse getCategoryByIdResponse;
    APIResponse getItemsByCategoryByIdResponse;
    APIResponse deleteCategoryResponse;

    @BeforeEach
    void setUp() {
        categoryResponseDto = CategoryResponseDto.builder()
                .categoryId("1")
                .name("Vegetables")
                .description("This is Vegetables")
                .build();
        categoryRequestDto = CategoryRequestDto.builder()
                .name("Vegetables")
                .description("This is Vegetables")
                .build();
        category = Category.builder()
                .categoryId("1C")
                .name("Vegetables")
                .description("This is Vegetables")
                .build();
        categories.add(categoryResponseDto);
        addCategoryResponse = APIResponse.builder()
                .data(categoryResponseDto)
                .status(HttpStatus.CREATED.value())
                .build();
        getCategoriesResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(categories)
                .build();
        getCategoryByIdResponse = APIResponse.builder()
                .data(categoryResponseDto)
                .status(HttpStatus.OK.value())
                .build();
        item = Item.builder()
                .itemId("1I")
                .itemName("Tomato")
                .price(20.0)
                .category(category)
                .build();
        items.add(item);
        getItemsByCategoryByIdResponse = APIResponse.builder()
                .data(items)
                .status(HttpStatus.OK.value())
                .build();
        deleteCategoryResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }

    @Test
    public void testAddCategory() {
        when(categoryService.addCategory(categoryRequestDto)).thenReturn(addCategoryResponse);
        ResponseEntity<APIResponse> addCategoryResponse = categoryController.addCategory(categoryRequestDto);
        assertEquals(HttpStatus.CREATED, addCategoryResponse.getStatusCode());
    }

    @Test
    public void testGetCategories() {
        when(categoryService.getCategories()).thenReturn(getCategoriesResponse);
        ResponseEntity<APIResponse> getCategoriesResponse = categoryController.getCategories();
        assertEquals(HttpStatus.OK, getCategoriesResponse.getStatusCode());
    }

    @Test
    void testGetCategoryById() {
        when(categoryService.getCategoryById(category.getCategoryId())).thenReturn(getCategoryByIdResponse);
        ResponseEntity<APIResponse> getCategoryByIdResponse = categoryController.getCategoryById(category.getCategoryId());
        assertEquals(HttpStatus.OK, getCategoryByIdResponse.getStatusCode());
    }

    @Test
    void testGetItemsByCategoryId() {
        when(categoryService.getItemsByCategoryId(category.getCategoryId())).thenReturn(getItemsByCategoryByIdResponse);
        ResponseEntity<APIResponse> getItemsByCategoryIdResponse = categoryController.getItemsByCategoryId(category.getCategoryId());
        assertEquals(HttpStatus.OK.value(), getItemsByCategoryByIdResponse.getStatus());
    }

    @Test
    void testDeleteCategory() {
        when(categoryService.deleteCategory(category.getCategoryId())).thenReturn(deleteCategoryResponse);
        ResponseEntity<APIResponse> deleteCategoryResponse = categoryController.deleteCategory(category.getCategoryId());
        assertEquals(HttpStatus.OK.value(), deleteCategoryResponse.getStatusCode().value());
    }
}
