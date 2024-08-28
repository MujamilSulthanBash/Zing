package com.i2i.zing.mapper;

import com.i2i.zing.dto.StockRequestDto;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;

public class StockMapper {
    public static StockRequestDto convertEntityToDto(Stock stock) {
        StockRequestDto stockRequestDto = StockRequestDto.builder()
                .stockId(stock.getStockId())
                .darkStoreId(stock.getDarkstore().getDarkStoreId())
                .itemId(stock.getItem().getItemId())
                .quantity(stock.getQuantity())
                .build();
        return stockRequestDto;
    }

    public static Stock convertDtoToEntity(StockRequestDto stockRequestDto) {
        Stock stock = Stock.builder()
                .stockId(stockRequestDto.getStockId())
                .darkstore(DarkStore.builder()
                        .darkStoreId(stockRequestDto.getDarkStoreId())
                        .build())
                .item(Item.builder()
                        .itemId(stockRequestDto.getItemId())
                        .build())
                .quantity(stockRequestDto.getQuantity())
                .build();
        return stock;
    }
}
