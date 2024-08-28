package com.i2i.zing.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {
    private int quantity;
    private Double totalPrice;
    private String cartId;
    private String itemId;
}
