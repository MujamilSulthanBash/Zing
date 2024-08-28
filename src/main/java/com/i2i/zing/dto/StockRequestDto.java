package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class represents the Dto for
 *     Stock details like Stock ID, DarkStore ID, item id, quantity.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDto {
    private String stockId;
    private String darkStoreId;
    private String itemId;
    private int quantity;
}
