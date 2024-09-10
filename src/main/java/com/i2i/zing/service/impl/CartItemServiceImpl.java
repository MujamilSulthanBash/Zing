package com.i2i.zing.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.ItemDisplayResponseDto;
import com.i2i.zing.dto.CartItemRequestDto;
import com.i2i.zing.exception.EntityAlreadyExistsException;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.mapper.CartItemMapper;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.repository.CartItemRepository;
import com.i2i.zing.service.CartService;
import com.i2i.zing.service.CartItemService;
import com.i2i.zing.service.ItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ItemService itemservice;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public APIResponse addCartItem(CartItemRequestDto cartItemRequestDto) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = CartItemMapper.convertToCartItem(cartItemRequestDto);
        List<CartItem> cartItems = cartService.getCartItemsOfCartAsObject(cartItem.getCart().getCartId());
        for (CartItem items : cartItems) {
            if (Objects.equals(items.getItem().getItemId(), cartItem.getItem().getItemId())) {
                logger.warn("Item with Id : {}already exists in the cart with Id : {}", cartItem.getItem().getItemId(), cartItem.getCart().getCartId());
                throw new EntityAlreadyExistsException("Item with Id : " + cartItem.getItem().getItemId()
                                                       + "already exists please update quantity, if you need.");
            }
        }
        ItemDisplayResponseDto itemDisplayResponseDto = itemservice.getItemDtoById(cartItemRequestDto.getItemId());
        cartItem.setPrice(itemDisplayResponseDto.getPrice());
        cartItem.setCart(cartService.getCartAsModel(cartItem.getCart().getCartId()));
        cartItem.setTotalPrice((double) cartItem.getQuantity() * itemDisplayResponseDto.getPrice());
        CartItem resultCartItem = cartItemRepository.save(cartItem);
        apiResponse.setData(CartItemMapper.convertToCartItemDto(resultCartItem));
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCartItems() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(cartItemRepository.findAll().stream()
                .map(CartItemMapper::convertToCartItemDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getCartItem(String cartItemId) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem with Id : " + cartItemId + " not found."));
        apiResponse.setData(cartItem);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteCartItem(String cartItemId) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem with Id : " + cartItemId + " not found to delete."));
        cartItemRepository.deleteById(cartItemId);
        apiResponse.setData("CartItem with Id : " + cartItemId + " has been deleted.");
        apiResponse.setStatus(HttpStatus.NO_CONTENT.value());
        return apiResponse;
    }

    @Override
    public APIResponse updateCartItem(CartItemRequestDto cartItemRequestDto) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = cartItemRepository.findById(cartItemRequestDto.getCartItemId())
                .orElseThrow(() -> new EntityNotFoundException("CartItem with Id : " + cartItemRequestDto.getCartItemId() + " not found to update."));
        CartItem requestBody = CartItemMapper.convertToUpdateDtoToCartItem(cartItemRequestDto);
        cartItem.setItem(requestBody.getItem());
        cartItem.setCart(requestBody.getCart());
        cartItem.setQuantity(requestBody.getQuantity());
        CartItem resultCart = cartItemRepository.save(cartItem);
        apiResponse.setData(resultCart);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

}