package com.i2i.zing.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockResponseDto {
    private String darkStoreId;
    private String itemId;
    private int quantity;
}
