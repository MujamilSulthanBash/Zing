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
import com.i2i.zing.service.CartItemService;

@ExtendWith(MockitoExtension.class)
public class CartItemControllerTest {
    @InjectMocks
    CartItemController cartItemController;

    @Mock
    CartItemService cartItemService;

    private String cartItemId;
    private CartItemRequestDto cartItemRequestDto;
    private APIResponse addCartItemResponse;
    private APIResponse getCartItemsResponse;
    private APIResponse getCartItemResponse;
    private APIResponse deleteCartItemResponse;
    private APIResponse updateCartItemResponse;

    @BeforeEach
    public void setUp() {
        cartItemId = "1ci";
        cartItemRequestDto = CartItemRequestDto.builder()
                .cartItemId(cartItemId)
                .cartId("1c")
                .itemId("1i")
                .quantity(2)
                .totalPrice(30.0)
                .build();
        addCartItemResponse = APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data(cartItemRequestDto).build();
        getCartItemsResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(List.of(cartItemRequestDto)).build();
        getCartItemResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(cartItemRequestDto).build();
        deleteCartItemResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("CartItem with Id : " + cartItemId + " has been deleted.").build();
    }

    @Test
    public void testAddCartItem() {
        CartItemRequestDto cartItemRequestDto1 = CartItemRequestDto.builder()
                .cartId("1c")
                .itemId("1i")
                .quantity(2).build();
        when(cartItemService.addCartItem(cartItemRequestDto1)).thenReturn(addCartItemResponse);
        ResponseEntity<APIResponse> addCartItemResponse = cartItemController.addCartItem(cartItemRequestDto1);
        assertEquals(HttpStatus.CREATED, addCartItemResponse.getStatusCode());
    }

    @Test
    public void testGetCartItems() {
        when(cartItemService.getCartItems()).thenReturn(getCartItemsResponse);
        ResponseEntity<APIResponse> getCartItemsResponse = cartItemController.getCartItems();
        assertEquals(HttpStatus.OK, getCartItemsResponse.getStatusCode());
    }

    @Test
    public void testGetCartItem() {
        when(cartItemService.getCartItem(cartItemId)).thenReturn(getCartItemResponse);
        ResponseEntity<APIResponse> getCartItemResponse = cartItemController.getCartItem(cartItemId);
        assertEquals(HttpStatus.OK, getCartItemResponse.getStatusCode());
    }

    @Test
    public void testDeleteCartItem() {
        when(cartItemService.deleteCartItem(cartItemId)).thenReturn(deleteCartItemResponse);
        ResponseEntity<APIResponse> deleteCartItemResponse = cartItemController.deleteCartItem(cartItemId);
        assertEquals(HttpStatus.OK, deleteCartItemResponse.getStatusCode());
    }

    @Test
    public void testUpdateCartItem() {
        CartItemRequestDto cartItemRequestDto1 = CartItemRequestDto.builder()
                .cartItemId(cartItemId)
                .cartId("1c")
                .itemId("1i")
                .quantity(4).build();
        CartItemRequestDto cartItemRequestDto2 = CartItemRequestDto.builder()
                .cartItemId(cartItemId)
                .cartId("1c")
                .itemId("1i")
                .quantity(4)
                .totalPrice(200.0).build();
        updateCartItemResponse = APIResponse.builder()
                .data(cartItemRequestDto2)
                .status(HttpStatus.OK.value()).build();
        when(cartItemService.updateCartItem(cartItemRequestDto1)).thenReturn(updateCartItemResponse);
        ResponseEntity<APIResponse> updateCartItemResponse = cartItemController.updateCartItem(cartItemRequestDto1);
        assertEquals(HttpStatus.OK, updateCartItemResponse.getStatusCode());
    }
}
