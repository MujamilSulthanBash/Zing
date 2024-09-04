package com.i2i.zing.service.impl;

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
import com.i2i.zing.dto.StockRequestDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.StockRepository;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {
    @Mock
    StockRepository stockRepository;

    @InjectMocks
    StockServiceImpl stockService;

    StockRequestDto stockRequestDto;

    Item item;

    Stock stock;

    @BeforeEach
    void setUp() {
        Item item = Item.builder()
                .itemId("1")
                .itemName("Tomato")
                .price(15.0)
                .category(Category.builder()
                        .name("Vegetables")
                        .categoryId("1c")
                        .description("This is Vegetables")
                        .build())
                .build();
        Set<Stock> stocks = new HashSet<>();
        stock = Stock.builder()
                .stockId("1L")
                .item(item)
                .darkstore(DarkStore.builder()
                        .darkStoreId("1")
                        .build())
                .isDeleted(false)
                .quantity(20)
                .build();
        stockRequestDto = StockRequestDto.builder()
                .stockId("1L")
                .darkStoreId("1")
                .itemId("1")
                .quantity(30)
                .build();
        stocks.add(stock);
    }

    @Test
    void testAddStocks() {
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        APIResponse apiResponse  = stockService.addStock(stockRequestDto);

        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void testGetAllStocks() {
        when(stockRepository.findByIsDeletedFalse()).thenReturn(List.of(stock));
        APIResponse apiResponse = stockService.getStocks();
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void testGetStockByIdSuccess(){
        when(stockRepository.findByIsDeletedFalseAndStockId("1L")).thenReturn(stock);
        APIResponse apiResponse = stockService.getStockById("1L");

        assertThat(apiResponse.getData()).isNotNull();
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testGetStocksByIdFailure() {
        when(stockRepository.findByIsDeletedFalseAndStockId("1L")).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            stockService.getStockById("1L");
        });
    }

    @Test
    void testGetStockByLocation() {
        when(stockRepository.findStocksByLocation("Guindy")).thenReturn(List.of(stock));
        List<Stock> stocks = stockService.getStocksByLocation("Guindy");

        assertEquals(1, stocks.size());
    }

    @Test
    void testGetStockByItemId() {
        when(stockRepository.findStockByItemId("1")).thenReturn(stock);
        Stock stockByItem = stockService.getStockByItemId("1");
        assertEquals(stock, stockByItem);
    }

    @Test
    void testUpdateStock() {
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(stockRepository.findByIsDeletedFalseAndStockId(stock.getStockId())).thenReturn(stock);
        APIResponse apiResponse = stockService.updateStock(stockRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testDeleteStock() {
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(stockRepository.findByIsDeletedFalseAndStockId("1L")).thenReturn(stock);

        APIResponse apiResponse = stockService.deleteStock("1L");
        assertEquals(apiResponse.getData(), "Stock Deleted Successfully : " + stock.getStockId());
    }

}
