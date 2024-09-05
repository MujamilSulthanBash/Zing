package com.i2i.zing.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.i2i.zing.dto.StockResponseDto;
import com.i2i.zing.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.StockRequestDto;
import com.i2i.zing.mapper.StockMapper;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.service.StockService;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    StockRepository stockRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Override
    public APIResponse addStock(StockRequestDto stockRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Stock stock = StockMapper.convertDtoToEntity(stockRequestDto);
        StockResponseDto stockResponseDto = StockMapper.convertEntityToDto(stockRepository.save(stock));
        apiResponse.setData(stock);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getStocks() {
        APIResponse apiResponse = new APIResponse();
        List<StockResponseDto> result = new ArrayList<>();
        List<Stock> stocks = stockRepository.findByIsDeletedFalse();
        for (Stock stock : stocks) {
            result.add(StockMapper.convertEntityToDto(stock));
        }
        if (result.isEmpty()) {
            logger.warn("Stock List is Empty..");
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
    public Stock getStockByItemId(String itemId) {
        return stockRepository.findStockByItemId(itemId);
    }

    @Override
    public APIResponse getStockById(String stockId) {
        APIResponse apiResponse = new APIResponse();
        Stock stock = stockRepository.findByIsDeletedFalseAndStockId(stockId);
        if (null == stock) {
            logger.warn("Stock Not found with Id :{}", stockId);
            throw new EntityNotFoundException("Stock Not found with Id :" + stockId);
        }
        StockMapper.convertEntityToDto(stock);
        apiResponse.setData(stock);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteStock(String stockId) {
        APIResponse apiResponse = new APIResponse();
        Stock stock = stockRepository.findByIsDeletedFalseAndStockId(stockId);
        if (null == stock) {
            logger.warn("Stock Not found to delete with Id :{}", stockId);
            throw new EntityNotFoundException("Stock Not found to delete with Id :" + stockId);
        }
        stock.setDeleted(true);
        stockRepository.save(stock);
        apiResponse.setData("Stock Deleted Successfully : " + stockId);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public void reduceStocks(Set<CartItem> cartItems) {
        APIResponse apiResponse = new APIResponse();
        final String to = "aravind.sureshkumar@ideas2it.com";
        for (CartItem cartItem : cartItems) {
            String itemId = cartItem.getItem().getItemId();
            int quantity = cartItem.getQuantity();
            List<Stock> stocks = stockRepository.findByIsDeletedFalse();
            for (Stock stock : stocks) {
                if (Objects.equals(itemId, stock.getItem().getItemId())) {
                    stock.setQuantity(stock.getQuantity() - quantity);
                    stockRepository.save(stock);
                    if (stock.getQuantity() <= 10) {
                        String subject = "Stock Refilling Alert";
                        String body = "Stock level at darkStore " + stock.getDarkstore().getDarkStoreId()
                                + " needs to be refilled. The current last reading : " + stock.getQuantity();
                        emailSenderService.sendEmail(to, subject, body);
                        logger.warn("Item {} has Minimum Stock Add more..", stock.getItem().getItemName());
                    }
                }
            }
        }
    }

    @Override
    public APIResponse updateStock(StockRequestDto stockRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Stock existingStock = stockRepository.findByIsDeletedFalseAndStockId(stockRequestDto.getStockId());
        if (null == existingStock) {
            logger.warn("Stock Not found to update with Id :{}", stockRequestDto.getStockId());
            throw new EntityNotFoundException("Stock Not found with Id :" + stockRequestDto.getStockId());
        }
        LocalDate modifiedDateTime = LocalDate.now();
        Date modifiedDate = Date.from(modifiedDateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        existingStock.setModifiedDate(modifiedDate);
        existingStock.setQuantity(stockRequestDto.getQuantity());
        stockRepository.save(existingStock);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(modifiedDateTime);
        return apiResponse;
    }

}
