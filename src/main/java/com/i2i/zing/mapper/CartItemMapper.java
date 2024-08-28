package com.i2i.zing.mapper;

import com.i2i.zing.dto.CartItemRequestDto;
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
     *
     * @param cartItem {@link CartItem} for conversion into dto.
     * @return CartItemRequestDto to display.
     */
    public static CartItemRequestDto convertToCartItemDto(CartItem cartItem) {
        return CartItemRequestDto.builder()
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
     *
     * @param cartItemRequestDto {@link CartItemRequestDto} to convert into entity.
     * @return CartItem  for internal usage.
     */
    public static CartItem convertToCartItem(CartItemRequestDto cartItemRequestDto) {
        return CartItem.builder()
                .item(Item.builder()
                        .itemId(cartItemRequestDto.getItemId()).build())
                .cart(Cart.builder()
                        .cartId(cartItemRequestDto.getCartId()).build())
                .quantity(cartItemRequestDto.getQuantity())
                .build();
    }

    /**
     * <p>
     *     Converts input dto to entity for updating cartItem.
     * </p>
     *
     * @param cartItemRequestDto {@link CartItemRequestDto} to convert into entity.
     * @return CartItem  for internal usage.
     */
    public static CartItem convertToUpdateDtoToCartItem(CartItemRequestDto cartItemRequestDto) {
        return CartItem.builder()
                .id(cartItemRequestDto.getCartItemId())
                .item(Item.builder()
                        .itemId(cartItemRequestDto.getItemId()).build())
                .cart(Cart.builder()
                        .cartId(cartItemRequestDto.getCartId()).build())
                .quantity(cartItemRequestDto.getQuantity())
                .build();
    }
}
