package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class represents the Dto for Multiple
 *     cart items Details like item id, quantity,
 *     price of the item, total price of the items
 *     based on quantity, payment Status, cart ID
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {
    private String cartItemId;
    private int quantity;
    private Double totalPrice;
    private String cartId;
    private String itemId;
}
