package com.i2i.zing.mapper;

import com.i2i.zing.dto.StockDto;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;

public class StockMapper {
    public static StockDto convertEntityToDto(Stock stock) {
        StockDto stockDto = StockDto.builder()
                .stockId(stock.getStockId())
                .darkStoreId(stock.getDarkstore().getDarkStoreId())
                .itemId(stock.getItem().getItemId())
                .quantity(stock.getQuantity())
                .build();
        return stockDto;
    }

    public static Stock convertDtoToEntity(StockDto stockDto) {
        Stock stock = Stock.builder()
                .stockId(stockDto.getStockId())
                .darkstore(DarkStore.builder()
                        .darkStoreId(stockDto.getDarkStoreId())
                        .build())
                .item(Item.builder()
                        .itemId(stockDto.getItemId())
                        .build())
                .quantity(stockDto.getQuantity())
                .build();
        return stock;
    }
}
