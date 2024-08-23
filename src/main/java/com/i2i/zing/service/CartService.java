package com.i2i.zing.service;

import com.i2i.zing.dto.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return CartDto value to indicate insertion status.
     */
    CartDto addCart(CartDto cartDto);

    /**
     * <p>
     *    Retrieves get the Carts allotted for all customers
     * </p>
     *
     * @return List<CartDto> value to display Cart list.
     */
    List<CartDto> getCarts();

    /**
     * <p>
     *     Fetches the Cart and returns the Cart.
     * </p>
     *
     * @param cartId - String value to display the Cart.
     * @return CartDto value to display the Cart.
     */
    CartDto getCart(String cartId);

    /**
     * <p>
     *     Removes the Cart from user view by changing boolean value.
     * </p>
     * @param cartId - String to remove Cart.
     * @return CartDto value to acknowledge.
     */
    CartDto deleteCart(String cartId);
}
