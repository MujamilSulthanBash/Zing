package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @Size(min = 3, max = 40, message = "stockId should contain 3 to 40 characters.")
    private String stockId;

    @NotBlank
    @Size(min = 3, max = 40, message = "darKStoreId should contain 3 to 40 characters.")
    private String darkStoreId;

    @NotBlank
    @Size(min = 3, max = 40, message = "itemId should contain 3 to 40 characters.")
    private String itemId;

    @NotBlank
    @Size(min = 10, max = 500, message = "quantity should contain 10 to 500 Kilograms.")
    @Pattern(regexp = "^[1-5]{1}[0-9]{2}$", message = "quantity should contain only numbers.")
    private int quantity;
}
