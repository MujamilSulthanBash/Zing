package com.i2i.zing.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CartItemRequestDto;
import com.i2i.zing.dto.ItemDisplayResponseDto;
import com.i2i.zing.exception.EntityAlreadyExistsException;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.repository.CartItemRepository;
import com.i2i.zing.service.impl.CartItemServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceImplTest {
    @InjectMocks
    CartItemServiceImpl cartItemServiceImpl;

    @Mock
    CartItemRepository cartItemRepository;

    @Mock
    CartService cartService;

    @Mock
    ItemService itemService;

    private Cart cart;
    private CartItem cartItem;
    private String cartItemId;
    private String cartId;
    private ItemDisplayResponseDto itemDisplayResponseDto;
    private CartItemRequestDto cartItemRequestDto;

    @BeforeEach
    public void setUp() {
        cartItemId = "1ci";
        cartId = "1c";
        cart = Cart.builder()
                .cartId(cartId)
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
        cartItem = CartItem.builder()
                .id("1ci")
                .cart(cart)
                .item(Item.builder()
                        .itemId("1i").build())
                .totalPrice(12.0)
                .quantity(2)
                .build();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        itemDisplayResponseDto = ItemDisplayResponseDto.builder()
                .categoryName("Vegetable")
                .name("Carrot")
                .price(12.0)
                .build();
        cartItemRequestDto = CartItemRequestDto.builder()
                .cartId("1c")
                .itemId("2i")
                .quantity(2)
                .totalPrice(12.0)
                .build();
    }

    @Test
    public void testAddCartItemSuccess() {
        when(cartService.getCartItemsOfCartAsObject(cartId)).thenReturn(List.of(cartItem));
        when(itemService.getItemDtoById("2i")).thenReturn(itemDisplayResponseDto);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        APIResponse apiResponse = cartItemServiceImpl.addCartItem(cartItemRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.CREATED.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testAddCartItemFailure() {
        CartItemRequestDto cartItemRequestDto = CartItemRequestDto.builder()
                .cartId("1c")
                .itemId("1i")
                .quantity(2)
                .totalPrice(12.0)
                .build();
        when(cartService.getCartItemsOfCartAsObject(cartId)).thenReturn(List.of(cartItem));
        EntityAlreadyExistsException thrown = assertThrows(EntityAlreadyExistsException.class, ()-> cartItemServiceImpl.addCartItem(cartItemRequestDto));
        assertEquals("Item with Id : " + cartItem.getItem().getItemId()
                     + "already exists please update quantity, if you need.", thrown.getMessage());
    }

    @Test
    public void testGetCartItems() {
        when(cartItemRepository.findAll()).thenReturn(List.of(cartItem));
        APIResponse apiResponse = cartItemServiceImpl.getCartItems();
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testGetCartItemSuccess() {
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.ofNullable(cartItem));
        APIResponse apiResponse = cartItemServiceImpl.getCartItem(cartItemId);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testDeleteCartItem() {
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.ofNullable(cartItem));
        APIResponse apiResponse = cartItemServiceImpl.deleteCartItem(cartItemId);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testUpdateCartItem() {
        CartItemRequestDto cartItemRequestDto = CartItemRequestDto.builder()
                .cartItemId(cartItemId)
                .cartId("1c")
                .itemId("2i")
                .quantity(3)
                .totalPrice(12.0)
                .build();
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.ofNullable(cartItem));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        APIResponse apiResponse = cartItemServiceImpl.updateCartItem(cartItemRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }
}
