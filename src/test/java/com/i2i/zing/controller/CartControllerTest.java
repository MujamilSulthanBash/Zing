package com.i2i.zing.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartItemRequestDto;
import com.i2i.zing.dto.CartResponseDto;
import com.i2i.zing.service.CartService;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
    @InjectMocks
    CartController cartController;

    @Mock
    CartService cartService;

    private APIResponse getCartResponse;
    private APIResponse getItemsOfCartResponse;
    private CartResponseDto cartResponseDto;
    private CartItemRequestDto cartItemRequestDto;
    private String cartId;

    @BeforeEach
    public void setUp() {
        cartId = "1c";
        cartResponseDto = CartResponseDto.builder()
                .cartId(cartId)
                .customerId("1cu")
                .build();
        cartItemRequestDto = CartItemRequestDto.builder()
                .quantity(2)
                .totalPrice(120.0)
                .itemId("1i")
                .cartItemId("1ci")
                .cartId(cartId).build();
        getCartResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(cartResponseDto).build();
        getItemsOfCartResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(List.of(cartItemRequestDto)).build();
    }

    @Test
    public void testGetCart() {
        when(cartService.getCart(cartId)).thenReturn(getCartResponse);
        ResponseEntity<APIResponse> getCartResponse = cartController.getCart(cartId);
        assertEquals(HttpStatus.OK, getCartResponse.getStatusCode());
    }

    @Test
    public void testGetItemsOfCart() {
        when(cartService.getCartItemsOfCart(cartId)).thenReturn(getItemsOfCartResponse);
        ResponseEntity<APIResponse> getItemsOfCartResponse = cartController.getItemsOfCart(cartId);
        assertEquals(HttpStatus.OK, getItemsOfCartResponse.getStatusCode());
    }
}