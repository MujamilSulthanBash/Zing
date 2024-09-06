package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.zing.dto.VerifyOrderDto;
import com.i2i.zing.exception.EntityAlreadyExistsException;
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
import static org.mockito.Mockito.when;

import com.i2i.zing.common.PaymentMethod;
import com.i2i.zing.common.PaymentStatus;
import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Order;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.OrderRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    @Mock
    OrderRepository orderRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private String cartId;
    private String orderId;
    private Cart cart;
    private CartItem cartItem;
    private Order order;
    private Order fourthOrder;
    private OrderDto orderDto;
    private Order firstOrder;
    private Order secondOrder;
    private Order thirdOrder;
    private List<Order> orders;
    private List<Order> firstOrders;
    private VerifyOrderDto verifyOrderDto;

    @BeforeEach
    public void setUp() {
        orderId = "100";
        cartId = "100";
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
                .orderId("100")
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.UPI)
                .cart(cart)
                .build();
        fourthOrder = Order.builder()
                .orderId("100")
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.UPI)
                .otp(encoder.encode("1234"))
                .cart(cart)
                .build();
        orderDto = OrderDto.builder()
                .orderId("100")
                .paymentStatus("PAID")
                .paymentMethod("UPI")
                .cartId("100").build();
        firstOrder = Order.builder()
                .orderId("100")
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.UPI)
                .cart(cart)
                .build();
        secondOrder = Order.builder()
                .orderId("200")
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.UPI)
                .cart(cart)
                .build();
        thirdOrder = Order.builder()
                .orderId("300")
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.UPI)
                .cart(cart)
                .build();
        orders = new ArrayList<>();
        firstOrders = new ArrayList<>();
        orders.add(order);
        orders.add(firstOrder);
        verifyOrderDto = VerifyOrderDto.builder()
                .otp("1234")
                .orderId("123456")
                .build();
        firstOrders.add(secondOrder);
        firstOrders.add(thirdOrder);
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

    @Test
    public void testUpdateOrderStatusFailure() {
        when(orderRepository.findByOrderIdAndIsDeletedFalse(verifyOrderDto.getOrderId())).thenReturn(order);
        assertEquals(orderServiceImpl.updateOrderStatus(verifyOrderDto).getStatus(), HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void testUpdateOrderStatusSuccess() {
        when(orderRepository.findByOrderIdAndIsDeletedFalse(verifyOrderDto.getOrderId())).thenReturn(fourthOrder);
        assertEquals(orderServiceImpl.updateOrderStatus(verifyOrderDto).getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void testAddOrderFailure() {
        when(orderRepository.findByIsDeletedFalse()).thenReturn(firstOrders);
        assertThrows(EntityAlreadyExistsException.class, () -> {
            orderServiceImpl.addOrder(orderDto);
        });
    }

}
