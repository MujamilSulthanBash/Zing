package com.i2i.zing.mapper;

import com.i2i.zing.dto.CartItemDto;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.model.Item;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class CartItemMapper {
    /**
     * <p>
     *     Converts the entity to displayable dto format.
     * </p>
     * @param cartItem {@link CartItem} for conversion into dto.
     * @return CartItemDto to display.
     */
    public static CartItemDto convertToCartItemDto(CartItem cartItem) {
        return CartItemDto.builder()
                .cartItemId(cartItem.getId())
                .itemId(cartItem.getItem().getItemId())
                .cartId(cartItem.getCart().getCartId())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }

    /**
     * <p>
     *     Converts input dto to entity for creating cartItem.
     * </p>
     * @param cartItemDto {@link CartItemDto} to convert into entity.
     * @return CartItem  for internal usage.
     */
    public static CartItem convertToCartItem(CartItemDto cartItemDto) {
        return CartItem.builder()
                .item(Item.builder()
                        .itemId(cartItemDto.getItemId()).build())
                .cart(Cart.builder()
                        .cartId(cartItemDto.getCartId()).build())
                .quantity(cartItemDto.getQuantity())
                .build();
    }
}
