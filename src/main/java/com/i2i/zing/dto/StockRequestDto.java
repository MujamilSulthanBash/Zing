package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class represents the Dto for
 * Stock details like Stock ID, DarkStore ID, item id, quantity.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDto {
    private String stockId;

    @NotBlank
    @Size(min = 3, max = 40, message = "darKStoreId should contain 3 to 40 characters.")
    private String darkStoreId;

    private String itemId;

    @NotNull
    private int quantity;
}
