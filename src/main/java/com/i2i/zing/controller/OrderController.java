package com.i2i.zing.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.i2i.zing.configuration.JwtService;
import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.service.OrderService;

/**
 * <p>
 * This class is the Controller for Order Operations
 * like Add, Update, Read and Delete the Order.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/customers/me/orders")
public class OrderController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    /**
     * <p>
     * This method add the Order to the Database
     * </p>
     *
     * @param orderDto {@link OrderDto} - Order as Dto Object
     * @return - APIResponse like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addOrder(
            @RequestHeader(value = "authorization", defaultValue = "") String auth,
            @Valid @RequestBody OrderDto orderDto) {
        APIResponse apiResponse = orderService.addOrder(
                orderDto, jwtService.getSubjectFromToken(auth));
        if (null == apiResponse.getData()) {
            logger.warn("Error Occurred while Adding Order..");
        } else {
            logger.info("Order Added Successfully..");
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get the Order Details in the Database
     * </p>
     *
     * @return APIResponse like Status, Data.
     */
    @GetMapping
    public ResponseEntity<APIResponse> getOrdersOfCustomer(
            @RequestHeader(value = "authorization", defaultValue = "") String auth) {
        APIResponse apiResponse = orderService.getOrdersOfCustomerById(
                jwtService.getSubjectFromToken(auth));
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get the Specific Order Detail by ID
     * </p>
     *
     * @param orderId - To Identify the Order
     * @return APIResponse like Status, Data.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<APIResponse> getOrder(@PathVariable String orderId) {
        APIResponse apiResponse = orderService.getOrder(orderId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
