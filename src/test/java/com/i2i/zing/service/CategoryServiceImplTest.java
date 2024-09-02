package com.i2i.zing.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CategoryRequestDto;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;
import com.i2i.zing.repository.CategoryRepository;
import com.i2i.zing.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    Category category;

    CategoryRequestDto categoryRequestDto;

    @BeforeEach
    void setup() {
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
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepository.findByIsDeletedFalse()).thenReturn(List.of(category));
        APIResponse apiResponse = categoryService.getCategories();

        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void getCategoryById(){
        when(categoryRepository.findByIsDeletedFalseAndCategoryId("1")).thenReturn(category);
        APIResponse apiResponse = categoryService.getCategoryById(category.getCategoryId());

        assertThat(apiResponse.getData()).isNotNull();
    }
}
