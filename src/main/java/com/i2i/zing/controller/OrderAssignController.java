package com.i2i.zing.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.OrderAssignDto;
import com.i2i.zing.service.OrderAssignService;

/**
 * <p>
 * Acts as transaction between interface and application for
 * fetching and updating assigned orders to delivery person.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/admins/orderAssign")
public class OrderAssignController {

    @Autowired
    OrderAssignService orderAssignService;

    private static final Logger logger = LogManager.getLogger();

    /**
     * <p>
     * Retrieves the required assigned order detail.
     * </p>
     *
     * @param assignId - String value to fetch details.
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{assignId}")
    public ResponseEntity<APIResponse> getOrderAssign(String assignId) {
        APIResponse apiResponse = orderAssignService.getOrderAssign(assignId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * Retrieves all the assigned order detail.
     * </p>
     *
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping
    public ResponseEntity<APIResponse> getOrderAssigns() {
        APIResponse apiResponse = orderAssignService.getOrderAssigns();
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * Updates the required assigned order detail.
     * </p>
     *
     * @param orderAssignDto - {@link OrderAssignDto} value to fetch details.
     * @return APIResponse Details like Status, Data.
     */
    @PutMapping
    public ResponseEntity<APIResponse> updateOrderAssigns(@RequestBody OrderAssignDto orderAssignDto) {
        APIResponse apiResponse = orderAssignService.updateOrderAssign(orderAssignDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}
