package com.i2i.zing.service.impl;

import java.util.List;

import com.i2i.zing.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

import com.i2i.zing.dto.CartDto;
import com.i2i.zing.mapper.CartMapper;
import com.i2i.zing.model.Cart;
import com.i2i.zing.repository.CartRepository;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on Cart.
 * </p>
 */
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Override
    public CartDto addCart(CartDto cartDto) {
        Cart Cart = CartMapper.convertToCart(cartDto);
        Cart resultCart = cartRepository.save(Cart);
        return CartMapper.convertToCartDto(resultCart);
    }

    @Override
    public List<CartDto> getCarts() {
        return cartRepository.findByIsDeletedFalse().stream()
                .map(CartMapper::convertToCartDto).toList();
    }

    @Override
    public CartDto getCart(String cartId) {
        Cart cart = cartRepository.findByCartIdAndIsDeleted(cartId, false);
        return CartMapper.convertToCartDto(cart);
    }

    @Override
    public void deleteCart(String cartId) {
        Cart cart = cartRepository.findByCartIdAndIsDeleted(cartId, false);
        cart.setDeleted(true);
        cartRepository.save(cart);
    }
}
