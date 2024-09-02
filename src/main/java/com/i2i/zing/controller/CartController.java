package com.i2i.zing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.service.CartService;

/**
 * <p>
 *     This class is the Controller for Cart Operations
 *     like Add, Update, Read and Delete the Cart.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/customers/carts")
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * <p>
     *     This method get the Cart Details by the Cart Id
     * </p>
     * @param cartId - To Identify the Cart
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<APIResponse> getCart(String cartId) {
        APIResponse apiResponse = cartService.getCart(cartId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get the Cart items of Specific
     *     Cart by Cart Id
     * </p>
     * @param cartId - To Identify the Cart
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{cartId}/cartItems")
    public ResponseEntity<APIResponse> getItemsOfCart(String cartId) {
        APIResponse apiResponse = cartService.getCartItemsOfCart(cartId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
