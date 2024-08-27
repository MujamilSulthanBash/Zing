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
@RequestMapping("zing/api/v1/cartitems")
public class CartItemController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<APIResponse> addCartItem(@RequestBody CartItemDto cartItemDto) {
        APIResponse apiResponse = cartItemService.addCartItem(cartItemDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getCartItems() {
        APIResponse apiResponse = cartItemService.getCartItems();
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<APIResponse> getCartItem(String cartItemId) {
        APIResponse apiResponse = cartItemService.getCartItem(cartItemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<APIResponse> deleteCartItem(String cartItemId) {
        APIResponse apiResponse = cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PutMapping
    public ResponseEntity<APIResponse> updateStock(@RequestBody CartItemDto cartItemDto) {
        APIResponse apiResponse = cartItemService.updateCartItem(cartItemDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}

