package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.i2i.zing.exception.EntityNotFoundException;
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
import com.i2i.zing.dto.ItemDisplayResponseDto;
import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.dto.ItemUpdateDto;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.ItemRepository;

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
    Item firstItem;

    Stock stock;
    Stock firstStock;

    List<Stock> stocks;
    List<Stock> firstStocks;
    List<Stock> thirdStocks;
    List<Item> items;

    @BeforeEach
    void setUp() {
        itemRequestDto = ItemRequestDto.builder().categoryId("1")
                .name("Tomato").price(15.0)
                .build();
        itemUpdateDto = ItemUpdateDto.builder().itemId("1I").name("Potato")
                .price(20.0)
                .build();
        item = Item.builder()
                .itemId("1I")
                .category(Category.builder().categoryId("1").name("Vegetables")
                        .description("This is Vegetables").isDeleted(false)
                        .build())
                .build();
        firstItem = Item.builder()
                .itemId("2I")
                .itemName("Tomato")
                .category(Category.builder().categoryId("2").name("Fruits")
                        .description("This is Fruits").isDeleted(false)
                        .build())
                .build();
        stock = Stock.builder()
                .stockId("1S")
                .quantity(13)
                .item(item)
                .isDeleted(false)
                .build();
        firstStock = Stock.builder()
                .stockId("2S")
                .quantity(5)
                .item(item)
                .isDeleted(false)
                .build();
        stocks = new ArrayList<>();
        firstStocks = new ArrayList<>();
        stocks.add(stock);
        firstStocks.add(firstStock);
        thirdStocks = new ArrayList<>();
        items = new ArrayList<>();
        items.add(firstItem);
    }

    @Test
    void testAddItems() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        APIResponse apiResponse  = itemService.addItem(itemRequestDto);
        assertThat(apiResponse.getData()).isNotNull();
        assertEquals(apiResponse.getStatus(), HttpStatus.CREATED.value());
    }

    @Test
    void testAddItemsFailure() {
        when(itemRepository.findByIsDeletedFalse()).thenReturn(items);
        assertThrows(EntityNotFoundException.class, () -> {
            itemService.addItem(itemRequestDto);
        });
    }

    @Test
    void testGetItemDtoById() {
        when(itemRepository.findByIsDeletedFalseAndItemId("1I")).thenReturn(item);
        ItemDisplayResponseDto itemDisplayResponseDto = itemService.getItemDtoById("1I");
        assertEquals(itemDisplayResponseDto.getName(), item.getItemName());
    }

    @Test
    void testGetItemDtoByIdFailure() {
        when(itemRepository.findByIsDeletedFalseAndItemId(any())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            itemService.getItemDtoById(item.getItemId());
        });
    }

    @Test
    void testUpdateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemRepository.findByIsDeletedFalseAndItemId(item.getItemId())).thenReturn(item);
        APIResponse apiResponse = itemService.updateItem(itemUpdateDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testUpdateItemFailure() {
        when(itemRepository.findByIsDeletedFalseAndItemId(item.getItemId())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            itemService.updateItem(itemUpdateDto);
        });
    }

    @Test
    void testDeleteItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemRepository.findByIsDeletedFalseAndItemId("1I")).thenReturn(item);
        APIResponse apiResponse = itemService.deleteItem("1I");
        assertEquals(apiResponse.getData(), "Item Deleted Successfully : " + item.getItemId());
    }

    @Test
    void testDeleteItemFailure() {
        when(itemRepository.findByIsDeletedFalseAndItemId(any())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            itemService.deleteItem(item.getItemId());
        });
    }

    @Test
    void testGetItemById() {
        when(itemRepository.findByIsDeletedFalseAndItemId("1I")).thenReturn(item);
        APIResponse apiResponse = itemService.getItemById("1I");
        assertThat(apiResponse.getData()).isNotNull();
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testGetItemByIdFailure() {
        when(itemRepository.findByIsDeletedFalseAndItemId(any())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            itemService.getItemById(item.getItemId());
        });
    }

    @Test
    void testGetItemsByLocation() {
        when(stockService.getStocksByLocation(any())).thenReturn(stocks);
        assertEquals(itemService.getItemsByLocation("Chennai").getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testGetItemsByLocationSuccess() {
        when(stockService.getStocksByLocation(any())).thenReturn(firstStocks);
        assertEquals(itemService.getItemsByLocation("Chennai").getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testGetItemsByLocationEmptyStocks() {
        when(stockService.getStocksByLocation(any())).thenReturn(thirdStocks);
        assertEquals(itemService.getItemsByLocation("Chennai").getStatus(), HttpStatus.OK.value());
    }

}
