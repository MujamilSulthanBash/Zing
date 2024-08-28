package com.i2i.zing.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartRequestDto;
import com.i2i.zing.exeception.EntityNotFoundException;
import com.i2i.zing.mapper.CartItemMapper;
import com.i2i.zing.mapper.CartMapper;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.repository.CartRepository;
import com.i2i.zing.service.CartService;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on Cart.
 * </p>
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public APIResponse addCart(CartRequestDto cartRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Cart resultCart = cartRepository.save(CartMapper.convertToCart(cartRequestDto));
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
        if (null == cart) {
            logger.warn("Cart with Id : {} not found.", cartId);
            throw new EntityNotFoundException("Cart with Id : " + cartId + " not found.");
        }
        apiResponse.setData(cart);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteCart(String cartId) {
        APIResponse apiResponse = new APIResponse();
        Cart cart = cartRepository.findByCartIdAndIsDeleted(cartId, false);
        if (null == cart) {
            logger.warn("Cart with Id : {} not found to delete.", cartId);
            throw new EntityNotFoundException("Cart with Id : " + cartId + " not found to delete.");
        }
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
        if (null == cart) {
            logger.warn("Cart with Id : {} not found to fetch items.", cartId);
            throw new EntityNotFoundException("Cart with Id : " + cartId + " not found to fetch Items.");
        }
        apiResponse.setData(cart.getCartItems().stream()
                .map(CartItemMapper::convertToCartItemDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public List<CartItem> getCartItemsOfCartAsObject(String cartId) {
        Cart cart = cartRepository.findByCartIdAndIsDeleted(cartId, false);
        if (null == cart) {
            logger.warn("Cart with Id : {} not found to fetch item objects.", cartId);
            throw new EntityNotFoundException("Cart with Id : " + cartId + " not found to fetch Items.");
        }
        return cart.getCartItems().stream().toList();
    }
}
