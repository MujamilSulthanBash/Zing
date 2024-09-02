package com.i2i.zing.service;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.Membership;
import com.i2i.zing.dto.CartItemRequestDto;
import com.i2i.zing.dto.CartResponseDto;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.model.Item;
import com.i2i.zing.repository.CartRepository;
import com.i2i.zing.service.impl.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    @InjectMocks
    private CartServiceImpl cartServiceImpl;

    @Mock
    private CartRepository cartRepository;

    private Cart cart;
    private CartItem cartItem;
    private Customer customer;
    private CartResponseDto cartResponseDto;
    private CartItemRequestDto cartItemRequestDto;
    private String cartId;
    @BeforeEach
    public void setUp() {
        cartId = "1c";
        customer = Customer.builder()
                .customerId("1cu")
                .memberShip(Membership.SILVER)
                .carts(Set.of(Cart.builder()
                        .cartId("1c").build()))
                .build();
        cart = Cart.builder()
                .cartId(cartId)
                .customer(customer)
                .cartItems(Set.of(CartItem.builder()
                        .id("1ci")
                        .cart(cart)
                        .item(Item.builder()
                                .itemId("1i").build())
                        .totalPrice(12.0)
                        .quantity(2)
                        .build()))
                .build();
        cartItem = CartItem.builder()
                .id("1ci")
                .cart(cart)
                .item(Item.builder()
                        .itemId("1i").build())
                .totalPrice(12.0)
                .quantity(2)
                .build();
        cartResponseDto = CartResponseDto.builder()
                          .cartId("1c")
                          .customerId("1cu")
                          .build();
        cartItemRequestDto = CartItemRequestDto.builder()
                             .cartItemId("1ci")
                             .cartId("1c")
                             .itemId("1i")
                             .quantity(2)
                             .totalPrice(12.0)
                             .build();
    }

    @Test
    public void testGetCarts() {
//        Customer customer1 = Customer.builder().customerId("1c").build();
//        Cart cart = Cart.builder().cartId(cartId).customer(customer1).build();
        when(cartRepository.findAll()).thenReturn(List.of(cart));
        APIResponse apiResponse = cartServiceImpl.getCarts();
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertEquals(apiResponse.getData(), isNotNull());
    }
}
