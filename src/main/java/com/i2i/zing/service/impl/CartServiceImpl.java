package com.i2i.zing.service.impl;

import com.i2i.zing.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartDto;
import com.i2i.zing.mapper.CartItemMapper;
import com.i2i.zing.mapper.CartMapper;
import com.i2i.zing.model.Cart;
import com.i2i.zing.repository.CartRepository;
import com.i2i.zing.service.CartService;

import java.util.List;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on Cart.
 * </p>
 */
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Override
    public APIResponse addCart(CartDto cartDto) {
        APIResponse apiResponse = new APIResponse();
        Cart resultCart = cartRepository.save(CartMapper.convertToCart(cartDto));
        apiResponse.setData(resultCart);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCarts() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(cartRepository.findByIsDeletedFalse().stream()
                .map(CartMapper::convertToCartDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCart(String cartId) {
        APIResponse apiResponse = new APIResponse();
        Cart cart = cartRepository.findByCartIdAndIsDeleted(cartId, false);
        apiResponse.setData(cart);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteCart(String cartId) {
        APIResponse apiResponse = new APIResponse();
        Cart cart = cartRepository.findByCartIdAndIsDeleted(cartId, false);
        cart.setDeleted(true);
        cartRepository.save(cart);
        apiResponse.setData("Cart with Id : " + cartId + " has been deleted.");
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCartItemsOfCart(String cartId) {
        APIResponse apiResponse = new APIResponse();
        Cart cart = cartRepository.findByCartIdAndIsDeleted(cartId, false);
        apiResponse.setData(cart.getCartItems().stream()
                .map(CartItemMapper::convertToCartItemDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public List<CartItem> getCartItemsOfCartAsObject(String cartId) {
        Cart cart = cartRepository.findByCartIdAndIsDeleted(cartId, false);
        return cart.getCartItems().stream().toList();
    }
}
