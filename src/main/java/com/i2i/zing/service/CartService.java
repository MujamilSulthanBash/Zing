package com.i2i.zing.service;

import java.util.List;

import com.i2i.zing.common.APIResponse;
import org.springframework.stereotype.Service;

import com.i2i.zing.dto.CartDto;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on Cart.
 * </p>
 */
@Service
public interface CartService {

    /**
     * <p>
     *     passes the value for insertion into the collection.
     * </p>
     *
     * @param cartDto - {@link CartDto} value to set Cart Id.
     * @return APIResponse value to indicate insertion status.
     */
    APIResponse addCart(CartDto cartDto);

    /**
     * <p>
     *    Retrieves get the Carts allotted for all customers
     * </p>
     *
     * @return APIResponse value to display Cart list.
     */
    APIResponse getCarts();

    /**
     * <p>
     *     Fetches the Cart and returns the Cart.
     * </p>
     *
     * @param cartId - String value to display the Cart.
     * @return APIResponse value to display the Cart.
     */
    APIResponse getCart(String cartId);

    /**
     * <p>
     *     Removes the Cart from user view by changing boolean value.
     * </p>
     * @param cartId - String to remove Cart.
     * @return @APIResponse value to acknowledge deletion.
     */
    APIResponse deleteCart(String cartId);
}
