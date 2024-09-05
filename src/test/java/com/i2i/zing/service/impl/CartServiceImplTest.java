package com.i2i.zing.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.Membership;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.model.Item;
import com.i2i.zing.repository.CartRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    @InjectMocks
    private CartServiceImpl cartServiceImpl;

    @Mock
    private CartRepository cartRepository;

    private Cart cart;
    private String cartId;
    private Customer customer;
    private Cart emptyCart;

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
        Set<CartItem> cartItems = new HashSet<>();
        CartItem cartItem = CartItem.builder()
                .id("1ci")
                .cart(cart)
                .item(Item.builder()
                        .itemId("1i").build())
                .totalPrice(12.0)
                .quantity(2)
                .build();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        emptyCart = null;
    }

    @Test
    public void testAddCart() {
        cartRepository.save(cart);
        cartServiceImpl.addCart(customer);
    }

    @Test
    public void testGetCarts() {
        when(cartRepository.findAll()).thenReturn(List.of(cart));
        APIResponse apiResponse = cartServiceImpl.getCarts();
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testGetCartSuccess() {
        when(cartRepository.findByCartId(cartId)).thenReturn(cart);
        APIResponse apiResponse = cartServiceImpl.getCart(cartId);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testGetCartFailure() {
        when(cartRepository.findByCartId(cartId)).thenReturn(emptyCart);
        assertThrows(EntityNotFoundException.class, ()-> cartServiceImpl.getCart(cartId));
    }

    @Test
    public void testGetCartAsModelSuccess() {
        when(cartRepository.findByCartId(cartId)).thenReturn(cart);
        Cart cart = cartServiceImpl.getCartAsModel(cartId);
        assertEquals(cart.getCartId(), cartId);
    }

    @Test
    public void testGetCartAsModelFailure() {
        when(cartRepository.findByCartId(cartId)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> cartServiceImpl.getCartAsModel(cartId));
    }

    @Test
    public void testGetCartItemsOfCartSuccess() {
        when(cartRepository.findByCartId(cartId)).thenReturn(cart);
        APIResponse apiResponse = cartServiceImpl.getCartItemsOfCart(cartId);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testGetCartItemsOfCartFailure() {
        when(cartRepository.findByCartId(cartId)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> cartServiceImpl.getCartItemsOfCart(cartId));
        assertEquals("Cart with Id : " + cartId + " not found to fetch Items.", thrown.getMessage());
    }

    @Test
    public void testGetCartItemsOfCartAsObjectSuccess() {
        when(cartRepository.findByCartId(cartId)).thenReturn(cart);
        List<CartItem> cartItems = cartServiceImpl.getCartItemsOfCartAsObject(cartId);
        assertEquals( 1, cartItems.size());
    }

    @Test
    public void testGetCartItemsOfCartAsObjectFailure() {
        when(cartRepository.findByCartId(cartId)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> cartServiceImpl.getCartItemsOfCartAsObject(cartId));
        assertEquals("Cart with Id : " + cartId + " not found to fetch Items.", thrown.getMessage());
    }
}
