package com.i2i.zing.dto;

import com.i2i.zing.model.CartItem;
import lombok.*;

import java.util.Set;

/**
 * <p>
 *     This class represents the Dto for
 *     Cart details like Cart ID, Customer ID
 *     and Cart Items Set
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String cartId;
    private String customerId;
    private Set<CartItem> cartItems;
}
