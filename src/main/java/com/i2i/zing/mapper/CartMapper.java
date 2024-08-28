package com.i2i.zing.mapper;

import com.i2i.zing.common.PaymentMethod;
import com.i2i.zing.dto.CartRequestDto;
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
    public static CartRequestDto convertToCartDto(Cart cart) {
        return CartRequestDto.builder()
                .cartId(cart.getCartId())
                .customerId(cart.getCartId())
                .paymentMethod(cart.getPaymentMethod().toString())
                .totalAmount(cart.getTotalAmount())
                .build();
    }

    /**
     * <p>
     *     Converts input dto to entity for creating cart.
     * </p>
     * @param cartRequestDto {@link CartRequestDto} to convert into entity.
     * @return Cart to for internal usage.
     */
    public static Cart convertToCart(CartRequestDto cartRequestDto) {
        return Cart.builder()
                .customer(Customer.builder()
                        .customerId(cartRequestDto.getCartId())
                        .build())
                .paymentMethod("CASHON".equals(cartRequestDto.getPaymentMethod()) ? PaymentMethod.CASHON : PaymentMethod.UPI)
                .build();
    }
}
