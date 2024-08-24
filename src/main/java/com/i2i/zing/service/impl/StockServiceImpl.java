package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.service.CartService;
import com.i2i.zing.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.dto.StockDto;
import com.i2i.zing.mapper.StockMapper;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    StockRepository stockRepository;

    @Autowired
    CartService cartService;

    @Override
    public APIResponse addStock(StockDto stockDto) {
        APIResponse apiResponse = new APIResponse();
        Stock stock = StockMapper.convertDtoToEntity(stockDto);
        StockMapper.convertEntityToDto(stockRepository.save(stock));
        apiResponse.setData(stock);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getStocks() {
        APIResponse apiResponse = new APIResponse();
        List<StockDto> result = new ArrayList<>();
        List<Stock> stocks = stockRepository.findByIsDeletedFalse();
        for (Stock stock : stocks) {
            result.add(StockMapper.convertEntityToDto(stock));
        }
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getStockById(String stockId) {
        APIResponse apiResponse = new APIResponse();
        Stock stock = stockRepository.findByIsDeletedFalseAndStockId(stockId);
        StockMapper.convertEntityToDto(stock);
        apiResponse.setData(stock);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteStock(String stockId) {
        APIResponse apiResponse = new APIResponse();
        Stock stock = stockRepository.findByIsDeletedFalseAndStockId(stockId);
        stock.setDeleted(true);
        stockRepository.save(stock);
        apiResponse.setData("Stock Deleted Successfully : " + stockId);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse reduceStocks(Set<CartItem> cartItems) {
        APIResponse apiResponse = new APIResponse();
        for (CartItem cartItem : cartItems) {
            String itemId = cartItem.getItem().getItemId();
            int quantity = cartItem.getQuantity();
            List<Stock> stocks = stockRepository.findByIsDeletedFalse();
            for (Stock stock : stocks) {
                if (itemId == stock.getItem().getItemId()) {
                    stock.setQuantity(stock.getQuantity() - quantity);
                }
            }
        }
        return apiResponse;
    }


}
