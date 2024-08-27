package com.i2i.zing.controller;

import com.i2i.zing.dto.CartDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.service.CartService;

/**
 * <p>
 *     This class is the Controller for Cart Operations
 *     like Add, Update, Read and Delete the Cart.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/carts")
public class CartController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    CartService cartService;

    /**
     * <p>
     *     This method add the Cart in the Database
     *     to add Cart Items
     * </p>
     * @param cartDto {@link CartDto} - Cart as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addCart(@RequestBody CartDto cartDto) {
        APIResponse apiResponse = cartService.addCart(cartDto);
        if (null == apiResponse.getData()) {
            logger.warn("Error Occurred while Adding Cart..");
        } else {
            logger.info("Cart Added Successfully..");
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

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
     *     This method delete the Cart by CartId
     * </p>
     * @param cartId - To Identify the Cart
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<APIResponse> deleteCart(String cartId) {
        APIResponse apiResponse = cartService.deleteCart(cartId);
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
    @GetMapping("/{cartId")
    public ResponseEntity<APIResponse> getItemsOfCart(String cartId) {
        APIResponse apiResponse = cartService.getCartItemsOfCart(cartId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}
