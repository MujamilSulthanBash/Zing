package com.i2i.zing.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.mapper.CartItemMapper;
import com.i2i.zing.mapper.CartMapper;
import com.i2i.zing.model.Customer;
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
    public void addCart(Customer customer) {
        APIResponse apiResponse = new APIResponse();
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cartRepository.save(cart);
    }

    @Override
    public APIResponse getCarts() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(cartRepository.findAll().stream()
                .map(CartMapper::convertEntityToDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCart(String cartId) {
        APIResponse apiResponse = new APIResponse();
        Cart cart = cartRepository.findByCartId(cartId);
        if (null == cart) {
            logger.warn("An Error Occurred while getting Cart with Id : {} not found.", cartId);
            throw new EntityNotFoundException("Cart Not found with Id : " + cartId);
        }
        apiResponse.setData(cart);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public Cart getCartAsModel(String cartId) {
        Cart cart = cartRepository.findByCartId(cartId);
        if (null == cart) {
            logger.warn("Cart with Id : {} not found.", cartId);
            throw new EntityNotFoundException("Cart with Id : " + cartId + " not found.");
        }
        return cart;
    }

    @Override
    public APIResponse getCartItemsOfCart(String cartId) {
        APIResponse apiResponse = new APIResponse();
        Cart cart = cartRepository.findByCartId(cartId);
        if (null == cart) {
            logger.warn("An Error Occurred while Getting Items by Cart with Id : {} not found", cartId);
            throw new EntityNotFoundException("Cart with Id : " + cartId + " not found to fetch Items.");
        }
        apiResponse.setData(cart.getCartItems().stream()
                .map(CartItemMapper::convertToCartItemDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public List<CartItem> getCartItemsOfCartAsObject(String cartId) {
        Cart cart = cartRepository.findByCartId(cartId);
        if (null == cart) {
            logger.warn("Cart with Id : {} not found to fetch item objects.", cartId);
            throw new EntityNotFoundException("Cart with Id : " + cartId + " not found to fetch Items.");
        }
        return cart.getCartItems().stream().toList();
    }

}
