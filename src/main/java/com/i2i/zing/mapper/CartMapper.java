package com.i2i.zing.mapper;

import com.i2i.zing.dto.CartResponseDto;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.Customer;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class CartMapper {

    /**
     * <p>
     *     Converts the entity to displayable dto format.
     * </p>
     * @param cart {@link Cart} for conversion into dto.
     * @return OrderDto to display.
     */
    public static CartResponseDto convertEntityToDto(Cart cart) {
        return CartResponseDto.builder()
                .cartId(cart.getCartId())
                .customerId(cart.getCustomer().getCustomerId())
                .build();
    }
}
