package com.i2i.zing.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.VerifyOrderDto;
import com.i2i.zing.service.OrderAssignService;
import com.i2i.zing.service.OrderService;

/**
 * <p>
 * This class is the Controller for Delivery Person Operations
 * Validate the order with OTP
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/deliverypersons")
public class DeliveryPersonController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderAssignService orderAssignService;

    @PostMapping("/orders/validate")
    public ResponseEntity<APIResponse> verifyOrder(@Valid @RequestBody VerifyOrderDto verifyOrderDto) {
        APIResponse apiResponse = orderService.updateOrderStatus(verifyOrderDto);
        if (apiResponse.getStatus() == HttpStatus.OK.value()) {
            orderAssignService.updateOrderStatus("DELIVERED", verifyOrderDto.getOrderId());
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
