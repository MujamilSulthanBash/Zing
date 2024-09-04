package com.i2i.zing.service;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.*;
import com.i2i.zing.dto.OrderAssignDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.model.*;
import com.i2i.zing.repository.OrderAssignRepository;
import com.i2i.zing.service.impl.OrderAssignServiceImpl;


@ExtendWith(MockitoExtension.class)
public class OrderAssignServiceImplTest {
    @InjectMocks
    OrderAssignServiceImpl orderAssignServiceImpl;

    @Mock
    OrderAssignRepository orderAssignRepository;

    @Mock
    DeliveryPersonService deliveryPersonService;

    @Mock
    CustomerService customerService;

    private Order order;
    private OrderAssign orderAssign;
    private Cart cart;
    private Customer customer;
    private DeliveryPerson deliveryPerson;
    private OrderAssignDto orderAssignDto;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
                .customerId("1cu")
                .memberShip(Membership.SILVER)
                .carts(Set.of(Cart.builder()
                        .cartId("1c").build()))
                .user(User.builder().location("Guindy").build())
                .build();
        cart = Cart.builder()
                .cartId("1c")
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
        order = Order.builder()
                .orderId("1o")
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.UPI)
                .cart(cart)
                .build();
        deliveryPerson = DeliveryPerson.builder()
                .deliveryPersonId("1d")
                .orderAssign(Set.of(OrderAssign.builder()
                        .order(Order.builder()
                                .cart(Cart.builder().cartId("2c").build()).build()).build()))
                .user(User.builder().location("Guindy").build())
                .build();
        orderAssign = OrderAssign.builder()
                .assignId("1a")
                .order(order)
                .deliveryPerson(deliveryPerson)
                .deliveryStatus(DeliveryStatus.PENDING).build();
        orderAssignDto = OrderAssignDto.builder()
                .orderId("1o")
                .assignId("1a")
                .deliveryStatus(DeliveryStatus.PENDING.toString())
                .deliveryPersonId("1d")
                .build();
    }

    @Test
    public void testGetOrderAssigns() {
        OrderAssignDto orderAssignDto = OrderAssignDto.builder()
                .orderId("1o")
                .assignId("1a")
                .deliveryStatus(DeliveryStatus.PENDING.toString())
                .deliveryPersonId("1d").build();
        when(orderAssignRepository.findByIsDeletedFalse()).thenReturn(List.of(orderAssign));
        APIResponse apiResponse = orderAssignServiceImpl.getOrderAssigns();
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testGetOrderAssignSuccess() {
        when(orderAssignRepository.findByAssignIdAndIsDeletedFalse("1a")).thenReturn(orderAssign);
        APIResponse apiResponse = orderAssignServiceImpl.getOrderAssign("1a");
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testGetOrderAssignFailure() {
        when(orderAssignRepository.findByAssignIdAndIsDeletedFalse("2a")).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> orderAssignServiceImpl.getOrderAssign("2a"));
        assertEquals("There is no assigning record with ID : " + "2a", thrown.getMessage());
    }

    @Test
    public void testUpdateOrderAssign() {
        OrderAssignDto orderAssignDto1 = OrderAssignDto.builder()
                .orderId("1o")
                .assignId("1a")
                .deliveryStatus(DeliveryStatus.ACCEPTED.toString())
                .deliveryPersonId("1d").build();
        OrderAssign orderAssign1 = OrderAssign.builder()
                .assignId("1a")
                .order(order)
                .deliveryPerson(deliveryPerson)
                .deliveryStatus(DeliveryStatus.ACCEPTED).build();
        when(orderAssignRepository.findByAssignIdAndIsDeletedFalse("1a")).thenReturn(orderAssign);
        orderAssign.setDeliveryStatus(DeliveryStatus.valueOf(orderAssignDto1.getDeliveryStatus()));
        when(orderAssignRepository.save(orderAssign)).thenReturn(orderAssign);
        APIResponse apiResponse = orderAssignServiceImpl.updateOrderAssign(orderAssignDto1);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    public void testUpdateOrderAssignFailure() {
        OrderAssignDto orderAssignDto1 = OrderAssignDto.builder()
                .orderId("1o")
                .assignId("2a")
                .deliveryStatus(DeliveryStatus.ACCEPTED.toString())
                .deliveryPersonId("1d").build();
        when(orderAssignRepository.findByAssignIdAndIsDeletedFalse("2a")).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> orderAssignServiceImpl.updateOrderAssign(orderAssignDto1));
        assertEquals("There is no assigning record with ID : " + orderAssignDto1.getAssignId() + " to update.", thrown.getMessage());
    }
}
