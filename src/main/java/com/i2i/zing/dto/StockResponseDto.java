package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class represents the Dto for Stock
 * Details like Stock Id, Dark Store Id, Item Id
 * and Quantity of the Item
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockResponseDto {
    private String stockId;
    private String darkStoreId;
    private String itemId;
    private int quantity;
}
