package com.i2i.zing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.i2i.zing.configuration.JwtService;
import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartItemRequestDto;
import com.i2i.zing.service.CartItemService;

/**
 * <p>
 * This class is the Controller for CartItem Operations
 * like Add, Update, Read and Delete the CartItems.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/customers/me/carts/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private JwtService jwtService;

    /**
     * <p>
     * This method add the Cart Items to the Database
     * </p>
     *
     * @param token - Authorization Token for user
     * @param cartItemRequestDto - {@link CartItemRequestDto}CartItem Details like ID, quantity
     *                           total price etc.,
     * @return - APIResponse (Status , Data)
     */
    @PostMapping
    public ResponseEntity<APIResponse> addCartItem(
            @RequestHeader(value = "authorization", defaultValue = "") String token,
            @Valid @RequestBody CartItemRequestDto cartItemRequestDto) {
        APIResponse apiResponse = cartItemService.addCartItem(cartItemRequestDto,
                jwtService.getSubjectFromToken(token));
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get all the Cart Items in the Database
     * </p>
     *
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
     * This method get the CartItem by ID
     * </p>
     *
     * @param cartItemId - To Identify the Cart Item
     * @return - APIResponse (Status, Data)
     */
    @GetMapping("/{cartItemId}")
    public ResponseEntity<APIResponse> getCartItem(@PathVariable String cartItemId) {
        APIResponse apiResponse = cartItemService.getCartItem(cartItemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method delete the Cart Item in the Database
     * </p>
     *
     * @param cartItemId - TO Identify the CartItem ID
     * @return - APIResponse (Status, Data)
     */
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<APIResponse> deleteCartItem(@PathVariable String cartItemId) {
        APIResponse apiResponse = cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method update the Stock
     * </p>
     *
     * @param cartItemRequestDto - {@link CartItemRequestDto}CartItem Details as Dto Object
     * @return - APIResponse (Status, Data)
     */
    @PutMapping
    public ResponseEntity<APIResponse> updateCartItem(
            @Valid @RequestBody CartItemRequestDto cartItemRequestDto) {
        APIResponse apiResponse = cartItemService.updateCartItem(cartItemRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}

