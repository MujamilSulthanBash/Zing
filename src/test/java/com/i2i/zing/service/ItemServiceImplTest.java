package com.i2i.zing.service;

import java.util.HashSet;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.ItemDisplayResponseDto;
import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.dto.ItemUpdateDto;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.ItemRepository;
import com.i2i.zing.service.impl.ItemServiceImpl;
import com.i2i.zing.service.impl.StockServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @Mock
    ItemRepository itemRepository;

    @Mock
    StockServiceImpl stockService;

    @InjectMocks
    ItemServiceImpl itemService;

    ItemRequestDto itemRequestDto;

    ItemUpdateDto itemUpdateDto;

    Item item;

    Stock stock;

    @BeforeEach
    void setUp() {
        itemRequestDto = ItemRequestDto.builder().categoryId("1")
                .name("Tomato").price(15.0)
                .build();
        itemUpdateDto = ItemUpdateDto.builder().itemId("1I").name("Potato")
                .price(20.0)
                .build();
        Set<Item> items = new HashSet<>();
        item = Item.builder()
                .itemId("1I")
                .category(Category.builder().categoryId("1").name("Vegetables")
                        .description("This is Vegetables").isDeleted(false)
                        .build())
                .build();
        items.add(item);
        stock = Stock.builder()
                .stockId("1S")
                .quantity(30)
                .item(item)
                .isDeleted(false)
                .build();
    }

    @Test
    void testAddItems() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        APIResponse apiResponse  = itemService.addItem(itemRequestDto);

        assertThat(apiResponse.getData()).isNotNull();
        assertEquals(apiResponse.getStatus(), HttpStatus.CREATED.value());
    }

    @Test
    void testGetItemDtoById() {
        when(itemRepository.findByIsDeletedFalseAndItemId("1I")).thenReturn(item);
        ItemDisplayResponseDto itemDisplayResponseDto = itemService.getItemDtoById("1I");

        assertEquals(itemDisplayResponseDto.getName(), item.getItemName());
    }

    @Test
    void testUpdateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemRepository.findByIsDeletedFalseAndItemId(item.getItemId())).thenReturn(item);
        APIResponse apiResponse = itemService.updateItem(itemUpdateDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testDeleteItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemRepository.findByIsDeletedFalseAndItemId("1I")).thenReturn(item);

        APIResponse apiResponse = itemService.deleteItem("1I");
        assertEquals(apiResponse.getData(), "Item Deleted Successfully : " + item.getItemId());
    }

    @Test
    void testGetItemById() {
        when(itemRepository.findByIsDeletedFalseAndItemId("1I")).thenReturn(item);
        APIResponse apiResponse = itemService.getItemById("1I");

        assertThat(apiResponse.getData()).isNotNull();
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }
}
