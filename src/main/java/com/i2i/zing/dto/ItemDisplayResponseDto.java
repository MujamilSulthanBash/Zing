package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class represents the Dto for Item
 * Details like Item name, price, Quantity, Status
 * Category Name
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDisplayResponseDto {
    private String itemId;
    private String name;
    private double price;
    private String categoryName;
    private int quantity;
    private String status;
}
