package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryRequestDto;
import com.i2i.zing.dto.CategoryResponseDto;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;
import com.i2i.zing.repository.CategoryRepository;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    Category category;

    CategoryRequestDto categoryRequestDto;

    CategoryResponseDto categoryResponseDto;

    List<CategoryResponseDto> result = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Set<Item> items = new HashSet<>();
        category = Category.builder()
                .categoryId("1")
                .name("Vegetables")
                .description("This is Vegetables")
                .build();
        Item item = Item.builder()
                .itemId("100")
                .category(category)
                .itemName("Tomato")
                .price(20.0)
                .isDeleted(false)
                .build();
        items.add(item);
        categoryRequestDto = CategoryRequestDto.builder()
                .name("Vegetables")
                .description("This is Vegetables")
                .build();
        categoryResponseDto = CategoryResponseDto.builder()
                .categoryId("1")
                .name("Vegetables")
                .description("This is Vegetables")
                .items(items)
                .build();
        result.add(categoryResponseDto);
        category.setItems(items);
    }

    @Test
    void testAddCategorySuccess() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        APIResponse apiResponse  = categoryService.addCategory(categoryRequestDto);

        assertEquals(apiResponse.getStatus(), HttpStatus.CREATED.value());
    }

    @Test
    void testGetAllCategoriesSuccess() {
        when(categoryRepository.findByIsDeletedFalse()).thenReturn(List.of(category));
        APIResponse apiResponse = categoryService.getCategories();
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void testGetCategoryByIdSuccess(){
        when(categoryRepository.findByIsDeletedFalseAndCategoryId("1")).thenReturn(category);
        APIResponse apiResponse = categoryService.getCategoryById(category.getCategoryId());

        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void testGetCategoryByIdFailure() {
        when(categoryRepository.findByIsDeletedFalseAndCategoryId("1")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> {
            categoryService.getCategoryById("1");
        });
    }

    @Test
    void testGetItemsByCategory() {
        when(categoryRepository.findByIsDeletedFalseAndCategoryId("1")).thenReturn(category);
        APIResponse apiResponse = categoryService.getItemsByCategoryId("1");
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void testDeleteCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryRepository.findByIsDeletedFalseAndCategoryId("1")).thenReturn(category);

        APIResponse apiResponse = categoryService.deleteCategory("1");
        assertEquals(apiResponse.getData(), "Category Deleted Successfully : " + category.getCategoryId());
    }
}
