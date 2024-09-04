package com.i2i.zing.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.PaymentMethod;
import com.i2i.zing.common.PaymentStatus;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.model.*;
import com.i2i.zing.repository.OrderRepository;
import com.i2i.zing.service.impl.EmailSenderService;
import com.i2i.zing.service.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderAssignService orderAssignService;

    @Mock
    StockService stockService;

    @Mock
    EmailSenderService emailSenderService;

    @Mock
    CartService cartService;

    private String cartId;
    private String orderId;
    private Cart cart;
    private CartItem cartItem;
    private Order order;
    private OrderDto orderDto;
    private Stock stock;

    @BeforeEach
    public void setUp() {
        orderId = "1o";
        cartId = "2c";
        cart = Cart.builder()
                .cartId(cartId)
                .cartItems(Set.of(CartItem.builder()
                        .id("1ci")
                        .cart(cart)
                        .item(Item.builder()
                                .itemId("1i").build())
                        .totalPrice(120.0)
                        .quantity(2)
                        .build()))
                .customer(Customer.builder()
                        .user(User.builder()
                                .emailId("email")
                                .build()).build())
                .build();
        Set<CartItem> cartItems = new HashSet<>();
        cartItem = CartItem.builder()
                .id("1ci")
                .cart(cart)
                .item(Item.builder()
                        .itemId("1i").build())
                .totalPrice(120.0)
                .quantity(2)
                .build();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        order = Order.builder()
                .orderId("1o")
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.UPI)
                .cart(cart)
                .build();
        orderDto = OrderDto.builder()
                .orderId("1o")
                .paymentStatus("PAID")
                .paymentMethod("UPI")
                .cartId("1c").build();
        stock = Stock.builder()
                .stockId("1s")
                .darkstore(DarkStore.builder().darkStoreId("1ds").build())
                .item(Item.builder().itemId("1i").build())
                .quantity(10).build();
    }

    @Test
    public void testGetOrders() {
        when(orderRepository.findByIsDeletedFalse()).thenReturn(List.of(order));
        APIResponse apiResponse = orderServiceImpl.getOrders();
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testGetOrderSuccess() {
        when(orderRepository.findByOrderIdAndIsDeletedFalse(orderId)).thenReturn(order);
        APIResponse apiResponse = orderServiceImpl.getOrder(orderId);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testGetOrderFailure() {
        String orderId = "2o";
        when(orderRepository.findByOrderIdAndIsDeletedFalse(orderId)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> orderServiceImpl.getOrder(orderId));
        assertEquals("Order with Id : " + orderId + " not found", thrown.getMessage());
    }

    @Test
    public void testGetOrderById() {
        when(orderRepository.findByOrderIdAndIsDeletedFalse(orderId)).thenReturn(order);
        Order order = orderServiceImpl.getOrderById(orderId);
        assertEquals(orderId, order.getOrderId());
    }

    @Test
    public void testGetOrderByIdFailure() {
        String orderId = "2o";
        when(orderRepository.findByOrderIdAndIsDeletedFalse(orderId)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> orderServiceImpl.getOrderById(orderId));
        assertEquals("Order with Id : " + orderId + " not found to verify.", thrown.getMessage());
    }
}
