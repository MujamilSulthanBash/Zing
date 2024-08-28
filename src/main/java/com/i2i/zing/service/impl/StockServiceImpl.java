package com.i2i.zing.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.service.CartService;
import com.i2i.zing.service.StockService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.dto.StockRequestDto;
import com.i2i.zing.mapper.StockMapper;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    StockRepository stockRepository;

    @Autowired
    CartService cartService;

    @Autowired
    EmailSenderService emailSenderService;

    @Override
    public APIResponse addStock(StockRequestDto stockRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Stock stock = StockMapper.convertDtoToEntity(stockRequestDto);
        StockMapper.convertEntityToDto(stockRepository.save(stock));
        apiResponse.setData(stock);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getStocks() {
        APIResponse apiResponse = new APIResponse();
        List<StockRequestDto> result = new ArrayList<>();
        List<Stock> stocks = stockRepository.findByIsDeletedFalse();
        for (Stock stock : stocks) {
            result.add(StockMapper.convertEntityToDto(stock));
        }
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public List<Stock> getStocksByLocation(String location) {
        return stockRepository.findStocksByLocation(location);
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
        final String to = "aravind.sureshkumar@ideas2it.com";
        for (CartItem cartItem : cartItems) {
            String itemId = cartItem.getItem().getItemId();
            int quantity = cartItem.getQuantity();
            List<Stock> stocks = stockRepository.findByIsDeletedFalse();
            for (Stock stock : stocks) {
                if (Objects.equals(itemId, stock.getItem().getItemId())) {
                    stock.setQuantity(stock.getQuantity() - quantity);
                    if(stock.getQuantity() <= 10) {
                        String subject = "Stock Refilling Alert";
                        String body = "Stock level at darkStore " + stock.getDarkstore().getDarkStoreId()
                                       + " needs to be refilled. The current last reading : " + stock.getQuantity();
                        emailSenderService.sendEmail(to, subject, body);
                        logger.warn("Item {} has Minimum Stock Add more..", stock.getItem().getItemName());
                    }
                }
            }
        }
        return apiResponse;
    }

    @Override
    public APIResponse updateStock(StockRequestDto stockRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Stock stock = StockMapper.convertDtoToEntity(stockRequestDto);
        Stock existingStock = stockRepository.findByIsDeletedFalseAndStockId(stockRequestDto.getItemId());
        LocalDate modifiedDateTime = LocalDate.now();
        Date modifiedDate = Date.from(modifiedDateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        existingStock.setModifiedDate(modifiedDate);
        stockRepository.save(existingStock);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(modifiedDateTime);
        return apiResponse;
    }
}
