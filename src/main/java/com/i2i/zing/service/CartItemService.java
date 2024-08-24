package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartItemDto;

public interface CartItemService {
    /**
     * <p>
     *     passes the value for insertion into the collection.
     * </p>
     *
     * @param cartItemDto - {@link CartItemDto} value to set Cart Id.
     * @return APIResponse value to indicate insertion status.
     */
    APIResponse addCartItem(CartItemDto cartItemDto);

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
     *     Removes the CartItem.
     * </p>
     * @param cartItemId - String to remove CartItem.
     * @return @APIResponse value to acknowledge deletion.
     */
    APIResponse deleteCartItem(String cartItemId);
}
