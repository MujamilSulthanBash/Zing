package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartItemDto;
import com.i2i.zing.service.CartItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     This class is the Controller for CartItem Operations
 *     like Add, Update, Read and Delete the CartItems.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/customers/cart/cartitems")
public class CartItemController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    CartItemService cartItemService;

    /**
     * <p>
     *     This method add the Cart Items to the Database
     * </p>
     * @param cartItemDto - CartItem Details like ID, quantity
     *                    total price etc.,
     * @return - APIResponse (Status , Data)
     */
    @PostMapping
    public ResponseEntity<APIResponse> addCartItem(@RequestBody CartItemDto cartItemDto) {
        APIResponse apiResponse = cartItemService.addCartItem(cartItemDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get all the Cart Items in the Database
     * </p>
     * @return - APIResponse (Status, Data)
     */
    @GetMapping
    public ResponseEntity<APIResponse> getCartItems() {
        APIResponse apiResponse = cartItemService.getCartItems();
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get the CartItem by ID
     * </p>
     * @param cartItemId - To Identify the Cart Item
     * @return - APIResponse (Status, Data)
     */
    @GetMapping("/{cartItemId}")
    public ResponseEntity<APIResponse> getCartItem(String cartItemId) {
        APIResponse apiResponse = cartItemService.getCartItem(cartItemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method delete the Cart Item in the Database
     * </p>
     * @param cartItemId - TO Identify the CartItem ID
     * @return - APIResponse (Status, Data)
     */
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<APIResponse> deleteCartItem(String cartItemId) {
        APIResponse apiResponse = cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method update the Stock
     * </p>
     * @param cartItemDto - CartItem Details as Dto Object
     * @return - APIResponse (Status, Data)
     */
    @PutMapping
    public ResponseEntity<APIResponse> updateCartItem(@RequestBody CartItemDto cartItemDto) {
        APIResponse apiResponse = cartItemService.updateCartItem(cartItemDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}

