package com.i2i.zing.mapper;

import com.i2i.zing.dto.StockRequestDto;
import com.i2i.zing.dto.StockResponseDto;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;

/**
 * <p>
 * Converts the json objects according to application operations.
 * e.g., (dto object -> Entity object, Entity object -> dto object)
 * </p>
 */
public class StockMapper {
    /**
     * <p>
     *     This method convert the entity Object to Dto Object
     * </p>
     * @param stock - Entity Object
     * @return StockResponseDto {@link StockResponseDto} - Stock Response as Dto Object
     */
    public static StockResponseDto convertEntityToDto(Stock stock) {
        return StockResponseDto.builder()
                .stockId(stock.getStockId())
                .darkStoreId(stock.getDarkstore().getDarkStoreId())
                .itemId(stock.getItem().getItemId())
                .quantity(stock.getQuantity())
                .build();
    }

    /**
     * <p>
     *     This method convert the Dto object to Entity Object
     * </p>
     * @param stockRequestDto {@link StockRequestDto} - Stock as Dto Object
     * @return Stock as Entity Object
     */
    public static Stock convertDtoToEntity(StockRequestDto stockRequestDto) {
        return Stock.builder()
                .stockId(stockRequestDto.getStockId())
                .darkstore(DarkStore.builder()
                        .darkStoreId(stockRequestDto.getDarkStoreId())
                        .build())
                .item(Item.builder()
                        .itemId(stockRequestDto.getItemId())
                        .build())
                .quantity(stockRequestDto.getQuantity())
                .build();
    }

}
