package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.StockRequestDto;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    @Mock
    StockServiceImpl stockService;

    @InjectMocks
    StockController stockController;

    Stock stock;

    Item item;

    List<Stock> stocks = new ArrayList<>();

    StockRequestDto stockRequestDto;

    APIResponse addStockResponse;

    APIResponse getStocksResponse;

    APIResponse getStockByIdResponse;

    APIResponse deleteStockResponse;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .itemId("1I")
                .price(40.0)
                .itemName("Tomato")
                .build();
        stock = Stock.builder()
                .stockId("1S")
                .quantity(30)
                .item(item)
                .build();
        stocks.add(stock);
        stockRequestDto = StockRequestDto.builder()
                .stockId("1S")
                .itemId("1I")
                .quantity(30)
                .build();
        addStockResponse = APIResponse.builder()
                .data(stockRequestDto)
                .status(HttpStatus.CREATED.value())
                .build();
        getStocksResponse = APIResponse.builder()
                .data(stocks)
                .status(HttpStatus.OK.value())
                .build();
        getStockByIdResponse = APIResponse.builder()
                .data(stock)
                .status(HttpStatus.OK.value())
                .build();
        deleteStockResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(null)
                .build();
    }

    @Test
    public void testAddCategory() {
        when(stockService.addStock(stockRequestDto)).thenReturn(addStockResponse);
        ResponseEntity<APIResponse> addStockResponse = stockController.addStock(stockRequestDto);
        assertEquals(HttpStatus.CREATED, addStockResponse.getStatusCode());
    }

    @Test
    public void testGetStocks() {
        when(stockService.getStocks()).thenReturn(getStocksResponse);
        ResponseEntity<APIResponse> getStocksResponse = stockController.getStocks();
        assertEquals(HttpStatus.OK, getStocksResponse.getStatusCode());
    }

    @Test
    public void testGetStockById() {
        when(stockService.getStockById(stockRequestDto.getStockId())).thenReturn(getStockByIdResponse);
        ResponseEntity<APIResponse> getStockByIdResponse = stockController.getStockById(stock.getStockId());
        assertEquals(HttpStatus.OK, getStockByIdResponse.getStatusCode());
    }

    @Test
    void testDeleteStock() {
        when(stockService.deleteStock(stock.getStockId())).thenReturn(deleteStockResponse);
        ResponseEntity<APIResponse> deleteStockResponse = stockController.deleteStock(stock.getStockId());
        assertEquals(deleteStockResponse.getStatusCode(), deleteStockResponse.getStatusCode());
    }
}
