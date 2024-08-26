package com.i2i.zing.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartItemDto;
import com.i2i.zing.dto.ItemDto;
import com.i2i.zing.exeception.EntityNotFoundException;
import com.i2i.zing.mapper.CartItemMapper;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.repository.CartItemRepository;
import com.i2i.zing.service.CartItemService;
import com.i2i.zing.service.ItemService;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ItemService itemservice;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public APIResponse addCartItem(CartItemDto cartItemDto) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = CartItemMapper.convertToCartItem(cartItemDto);
        ItemDto itemDto= itemservice.getItemDtoById(cartItemDto.getItemId());
        cartItem.setTotalPrice((double)cartItem.getQuantity() * itemDto.getPrice());
        CartItem resultCart = cartItemRepository.save(cartItem);
        apiResponse.setData(resultCart);
        apiResponse.setStatus(HttpStatus.OK.value());
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
                .orElseThrow(() -> new EntityNotFoundException("CartItem with Id : " + cartItemId + " not found to delete."));
        logger.warn("CartItem with Id : {} not found.", cartItemId);
        apiResponse.setData(cartItem);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteCartItem(String cartItemId) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem with Id : " + cartItemId + " not found to delete."));
        logger.warn("CartItem with Id : {} not found to delete.", cartItemId);
        cartItemRepository.deleteById(cartItemId);
        apiResponse.setData("CartItem with Id : " + cartItemId + " has been deleted.");
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse updateCartItem(CartItemDto cartItemDto) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = cartItemRepository.findById(cartItemDto.getCartItemId())
                .orElseThrow(() -> new EntityNotFoundException("CartItem with Id : " + cartItemDto.getCartItemId() + " not found to update."));
        logger.warn("CartItem with Id : {} not found to update.", cartItemDto.getCartItemId());
        CartItem requestBody = CartItemMapper.convertToUpdateDtoToCartItem(cartItemDto);
        cartItem.setItem(requestBody.getItem());
        cartItem.setCart(requestBody.getCart());
        cartItem.setQuantity(requestBody.getQuantity());
        CartItem resultCart = cartItemRepository.save(cartItem);
        apiResponse.setData(resultCart);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}