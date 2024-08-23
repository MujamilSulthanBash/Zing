package com.i2i.zing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.zing.dto.StockDto;
import com.i2i.zing.mapper.StockMapper;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    StockRepository stockRepository;

    @Override
    public StockDto addStock(StockDto stockDto) {
        Stock stock = StockMapper.convertDtoToEntity(stockDto);
        return StockMapper.convertEntityToDto(stockRepository.save(stock));
    }

    @Override
    public List<StockDto> getStocks() {
        List<StockDto> result = new ArrayList<>();
        List<Stock> stocks = stockRepository.findByIsDeletedFalse();
        for (Stock stock : stocks) {
            result.add(StockMapper.convertEntityToDto(stock));
        }
        return result;
    }

    @Override
    public StockDto getStockById(String stockId) {
        Stock stock = stockRepository.findByIsDeletedFalseAndStockId(stockId);
        return StockMapper.convertEntityToDto(stock);
    }

    @Override
    public void deleteStock(String stockId) {
        Stock stock = stockRepository.findByIsDeletedFalseAndStockId(stockId);
        stock.setDeleted(true);
        stockRepository.save(stock);
    }
}
