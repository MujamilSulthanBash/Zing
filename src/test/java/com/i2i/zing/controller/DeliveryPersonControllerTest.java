package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.i2i.zing.dto.VerifyOrderDto;
import com.i2i.zing.service.OrderAssignService;
import com.i2i.zing.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryPersonControllerTest {
    @InjectMocks
    DeliveryPersonController deliveryPersonController;

    @Mock
    OrderService orderService;

    @Mock
    OrderAssignService orderAssignService;

    private VerifyOrderDto verifyOrderDto;
    private APIResponse orderStatusResponse;

    @BeforeEach
    public void setUp() {
        verifyOrderDto = VerifyOrderDto.builder()
                .orderId("1o")
                .otp("1234").build();
        orderStatusResponse = APIResponse.builder()
                .status(HttpStatus.OK.value()).build();
    }

    @Test
    public void testVerifyOrder() {
        when(orderService.updateOrderStatus(verifyOrderDto)).thenReturn(orderStatusResponse);
        orderAssignService.updateOrderStatus("DELIVERED", "1o");
        ResponseEntity<APIResponse> orderStatusResponse = deliveryPersonController.verifyOrder(verifyOrderDto);
        assertEquals(HttpStatus.OK, orderStatusResponse.getStatusCode());
    }
}
