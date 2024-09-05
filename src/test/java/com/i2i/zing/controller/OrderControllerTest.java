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
import com.i2i.zing.common.PaymentMethod;
import com.i2i.zing.common.PaymentStatus;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.service.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    private String orderId;
    private OrderDto orderDto;
    private APIResponse addOrderResponse;
    private APIResponse getOrderResponse;
    private APIResponse getOrdersResponse;

    @BeforeEach
    public void setUp() {
        orderId = "1o";
        orderDto = OrderDto.builder()
                .orderId(orderId)
                .cartId("1c")
                .paymentMethod(PaymentMethod.UPI.toString())
                .paymentStatus(PaymentStatus.PAID.toString()).build();
        addOrderResponse = APIResponse.builder()
                .data(orderDto)
                .status(HttpStatus.CREATED.value()).build();
        getOrderResponse = APIResponse.builder()
                .data(orderDto)
                .status(HttpStatus.OK.value()).build();
        getOrdersResponse = APIResponse.builder()
                .data(List.of(orderDto))
                .status(HttpStatus.OK.value()).build();
    }

    @Test
    public void testAddOrder() {
        OrderDto orderDto1 = OrderDto.builder()
                .cartId("1c")
                .paymentMethod(PaymentMethod.UPI.toString())
                .build();
        when(orderService.addOrder(orderDto1)).thenReturn(addOrderResponse);
        ResponseEntity<APIResponse> addOrderResponse = orderController.addOrder(orderDto1);
        assertEquals(HttpStatus.CREATED, addOrderResponse.getStatusCode());
    }

    @Test
    public void testGetOrder() {
        when(orderService.getOrder(orderId)).thenReturn(getOrderResponse);
        ResponseEntity<APIResponse> getOrderResponse = orderController.getOrder(orderId);
        assertEquals(HttpStatus.OK, getOrderResponse.getStatusCode());
    }

    @Test
    public void testGetOrders() {
        when(orderService.getOrders()).thenReturn(getOrdersResponse);
        ResponseEntity<APIResponse> getOrdersResponse = orderController.getOrders();
        assertEquals(HttpStatus.OK, getOrdersResponse.getStatusCode());
    }
}
