package com.i2i.zing.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.i2i.zing.dto.UpdateOrderStatusDto;
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
import com.i2i.zing.common.DeliveryStatus;
import com.i2i.zing.dto.OrderAssignDto;
import com.i2i.zing.service.OrderAssignService;

@ExtendWith(MockitoExtension.class)
public class OrderAssignControllerTest {
    @InjectMocks
    OrderAssignController orderAssignController;

    @Mock
    OrderAssignService orderAssignService;

    private String assignId;
    private OrderAssignDto orderAssignDto;
    private UpdateOrderStatusDto updateOrderStatusDto;
    private APIResponse getOrderAssignResponse;
    private APIResponse getOrderAssignsResponse;
    private APIResponse updateOrderAssignResponse;

    @BeforeEach
    public void setUp() {
        assignId = "1a";
        orderAssignDto = OrderAssignDto.builder()
                .assignId(assignId)
                .orderId("1o")
                .deliveryStatus(DeliveryStatus.PENDING.toString())
                .deliveryPersonId("1d").build();
        updateOrderStatusDto = UpdateOrderStatusDto.builder().build();
        getOrderAssignResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(orderAssignDto).build();
        getOrderAssignsResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(List.of(orderAssignDto)).build();
    }

    @Test
    public void testGetOrderAssign() {
        when(orderAssignService.getOrderAssign(assignId)).thenReturn(getOrderAssignResponse);
        ResponseEntity<APIResponse> getOrderAssignResponse = orderAssignController.getOrderAssign(assignId);
        assertEquals(HttpStatus.OK, getOrderAssignResponse.getStatusCode());
    }

    @Test
    public void testGetOrderAssigns() {
        when(orderAssignService.getOrderAssigns()).thenReturn(getOrderAssignsResponse);
        ResponseEntity<APIResponse> getOrderAssignsResponse = orderAssignController.getOrderAssigns();
        assertEquals(HttpStatus.OK, getOrderAssignsResponse.getStatusCode());
    }

    @Test
    public void testUpdateOrderAssign() {
        OrderAssignDto orderAssignDto1 = OrderAssignDto.builder()
                .assignId(assignId)
                .deliveryStatus(DeliveryStatus.PICKEDUP.toString())
                .build();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        Date updatedTime = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        OrderAssignDto orderAssignDto2 = OrderAssignDto.builder()
                .assignId(assignId)
                .orderId("1o")
                .deliveryStatus(DeliveryStatus.PICKEDUP.toString())
                .deliveryPersonId("1d")
                .timeOfUpdate(updatedTime).build();
        updateOrderAssignResponse = APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(orderAssignDto2).build();
        when(orderAssignService.updateOrderAssign(orderAssignDto1)).thenReturn(updateOrderAssignResponse);
        ResponseEntity<APIResponse> updateOrderAssignResponse = orderAssignController.updateOrderAssign(orderAssignDto1);
        assertEquals(HttpStatus.OK, updateOrderAssignResponse.getStatusCode());
    }
}
