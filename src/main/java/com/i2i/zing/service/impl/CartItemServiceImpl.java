package com.i2i.zing.service.impl;

import com.i2i.zing.dto.CartItemRequestDto;
import com.i2i.zing.dto.ItemRequestDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
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
    public APIResponse addCartItem(CartItemRequestDto cartItemRequestDto) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = CartItemMapper.convertToCartItem(cartItemRequestDto);
        ItemRequestDto itemRequestDto = itemservice.getItemDtoById(cartItemRequestDto.getItemId());
        cartItem.setTotalPrice((double)cartItem.getQuantity() * itemRequestDto.getPrice());
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
    public APIResponse updateCartItem(CartItemRequestDto cartItemRequestDto) {
        APIResponse apiResponse = new APIResponse();
        CartItem cartItem = cartItemRepository.findById(cartItemRequestDto.getCartItemId())
                .orElseThrow(() -> new EntityNotFoundException("CartItem with Id : " + cartItemRequestDto.getCartItemId() + " not found to update."));
        logger.warn("CartItem with Id : {} not found to update.", cartItemRequestDto.getCartItemId());
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