package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartItemDto;
import com.i2i.zing.dto.ItemDto;
import com.i2i.zing.mapper.CartItemMapper;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.repository.CartItemRepository;
import com.i2i.zing.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ItemService itemservice;

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
    public APIResponse deleteCartItem(String cartItemId) {
        APIResponse apiResponse = new APIResponse();
        cartItemRepository.deleteById(cartItemId);
        apiResponse.setData("CartItem with Id : " + cartItemId + " has been deleted.");
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}