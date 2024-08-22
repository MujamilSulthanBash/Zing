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
public class CartItemDto {
    private String cartItemId;
    private Double quantity;
    private Double price;
    private Double totalPrice;
    private String paymentStatus;
    private String cartId;
    private String itemId;
}
