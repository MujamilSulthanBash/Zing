package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartItemRequestDto;

public interface CartItemService {

    /**
     * <p>
     *     passes the value for insertion into the collection.
     * </p>
     *
     * @param cartItemRequestDto - {@link CartItemRequestDto} value to set Cart Id.
     * @param auth - string value to fetch cartId.
     * @return APIResponse value to indicate insertion status.
     */
    APIResponse addCartItem(CartItemRequestDto cartItemRequestDto, String auth);

    /**
     * <p>
     *    Retrieves get the CartItems added in a cart for a customer
     * </p>
     *
     * @return APIResponse value to display CartItems in a list.
     */
    APIResponse getCartItems();

    /**
     * <p>
     *     Retrieves the CartItem.
     * </p>
     *
     * @param cartItemId - String to remove CartItem.
     * @return @APIResponse value to acknowledge deletion.
     */
    APIResponse getCartItem(String cartItemId);

    /**
     * <p>
     *     Removes the CartItem.
     * </p>
     *
     * @param cartItemId - String to remove CartItem.
     * @return @APIResponse value to acknowledge deletion.
     */
    APIResponse deleteCartItem(String cartItemId);

    /**
     * <p>
     *     Updates the cart Item by changing the item or quantity or cart.
     * </p>
     *
     * @param cartItemRequestDto {@link CartItemRequestDto} value to update cart Item.
     */
    APIResponse updateCartItem(CartItemRequestDto cartItemRequestDto);

}
