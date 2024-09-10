package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.ItemDisplayResponseDto;
import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.dto.LocationRequestDto;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import com.i2i.zing.service.impl.ItemServiceImpl;
import com.i2i.zing.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    @Mock
    ItemServiceImpl itemService;

    @Mock
    StockServiceImpl stockService;

    @InjectMocks
    ItemController itemController;

    Category category;

    Stock stock;

    List<Stock> stocks = new ArrayList<>();

    Map<String, ItemDisplayResponseDto> items = new HashMap<>();

    Item item;

    ItemRequestDto itemRequestDto;

    ItemDisplayResponseDto itemDisplayResponseDto;

    APIResponse addItemResponse;

    APIResponse getItemsByLocationResponse;

    APIResponse getItemByIdResponse;

    APIResponse deleteItemResponse;

    String location;

    @BeforeEach
    void setUp() {
        itemDisplayResponseDto = ItemDisplayResponseDto.builder()
                .name("Tomato")
                .price(30.0)
                .categoryName("Vegetables")
                .build();
        item = Item.builder()
                .itemId("1I")
                .itemName("Tomato")
                .price(30.0)
                .category(category)
                .build();
        stock = Stock.builder()
                .stockId("1S")
                .item(item)
                .quantity(30)
                .build();
        location = "Guindy";
        stocks.add(stock);
        items.put(stock.getItem().getItemName(), itemDisplayResponseDto);
        category = Category.builder()
                .categoryId("1C")
                .name("Vegetables")
                .description("This is Vegetables")
                .build();
        itemRequestDto = ItemRequestDto.builder()
                .price(40.0)
                .name("Tomato")
                .categoryId(category.getCategoryId())
                .build();
        addItemResponse = APIResponse.builder()
                .data(itemRequestDto)
                .status(HttpStatus.CREATED.value())
                .build();
        getItemsByLocationResponse = APIResponse.builder()
                .data(items)
                .status(HttpStatus.OK.value())
                .build();
        getItemByIdResponse = APIResponse.builder()
                .data(item)
                .status(HttpStatus.OK.value())
                .build();
        deleteItemResponse = APIResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .build();
    }

    @Test
    public void testAddItem() {
        when(itemService.addItem(itemRequestDto)).thenReturn(addItemResponse);
        ResponseEntity<APIResponse> addItemResponse = itemController.addItem(itemRequestDto);
        assertEquals(HttpStatus.CREATED, addItemResponse.getStatusCode());
    }

    @Test
    public void testGetItemsByLocation() {
        when(itemService.getItemsByLocation(location)).thenReturn(getItemsByLocationResponse);
        ResponseEntity<APIResponse> getItemsByLocationResponse = itemController.getItemsByLocation(location);
        assertEquals(HttpStatus.OK, getItemsByLocationResponse.getStatusCode());
    }

    @Test
    public void testGetItemById() {
        when(itemService.getItemById(item.getItemId())).thenReturn(getItemByIdResponse);
        ResponseEntity<APIResponse> getItemByIdResponse = itemController.getItemById(item.getItemId());
        assertEquals(HttpStatus.OK, getItemByIdResponse.getStatusCode());
    }

    @Test
    void testDeleteItem() {
        when(itemService.deleteItem(item.getItemId())).thenReturn(deleteItemResponse);
        ResponseEntity<APIResponse> deleteItemResponse = itemController.deleteItem(item.getItemId());
        assertEquals(HttpStatus.OK, deleteItemResponse.getStatusCode());
    }
}
