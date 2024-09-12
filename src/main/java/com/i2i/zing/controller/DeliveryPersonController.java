package com.i2i.zing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.configuration.JwtService;
import com.i2i.zing.dto.VerifyOrderDto;
import com.i2i.zing.dto.UpdateOrderStatusDto;
import com.i2i.zing.service.DeliveryPersonService;
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
    private JwtService jwtService;

    @Autowired
    private OrderAssignService orderAssignService;

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    /**
     * <>
     * Verifies the order by checking otp detail
     * given to customer through email.
     * </>
     * @param verifyOrderDto - {@link VerifyOrderDto} for verifying order.
     * @return APIResponse for deliveryPerson's acknowledgement.
     */
    @PutMapping("/orders/validate")
    public ResponseEntity<APIResponse> verifyOrder(
            @Valid @RequestBody VerifyOrderDto verifyOrderDto) {
        APIResponse apiResponse = orderService.updateOrderStatus(verifyOrderDto);
        if (apiResponse.getStatus() == HttpStatus.OK.value()) {
            APIResponse updateResponse = orderAssignService.updateOrderStatus(
                    "DELIVERED", verifyOrderDto.getOrderId());
            return ResponseEntity.status(updateResponse.getStatus())
                    .body(updateResponse);
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <>
     * Fetches the order details by checking respective
     * delivery person's Id.
     * </>
     * @return APIResponse for deliveryPerson's acknowledgement.
     */
    @GetMapping("/me/order-assigns")
    public ResponseEntity<APIResponse> getOrderAssignsOfDeliveryPerson(
            @RequestHeader(value = "authorization",
            defaultValue = "") String auth) {
        APIResponse apiResponse = deliveryPersonService.getOrderAssignsOfDeliveryPerson(
                jwtService.getSubjectFromToken(auth));
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * Retrieves the required assigned order detail.
     * </p>
     *
     * @param assignId - String value to fetch details.
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{assignId}/order-assign")
    public ResponseEntity<APIResponse> getOrderAssign(@PathVariable String assignId) {
        APIResponse apiResponse = orderAssignService.getOrderAssign(assignId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <>
     * Updates the assigned order status when it is
     * ready to dispatch.
     * </>
     * @param updateOrderStatusDto - {@link UpdateOrderStatusDto} for verifying order.
     * @return APIResponse for DeliveryPerson's acknowledgement.
     */
    @PutMapping("/order-status")
    public ResponseEntity<APIResponse> updateAssignStatus(
            @Valid @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        APIResponse updateResponse = orderAssignService.updateOrderAssign(
                updateOrderStatusDto);
        return ResponseEntity.status(updateResponse.getStatus())
                .body(updateResponse);
    }

}
