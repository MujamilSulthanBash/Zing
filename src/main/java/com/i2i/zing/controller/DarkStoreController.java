package com.i2i.zing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.dto.DarkStoreResponseDto;
import com.i2i.zing.dto.UpdateOrderStatusDto;
import com.i2i.zing.service.DarkStoreService;
import com.i2i.zing.service.OrderAssignService;

/**
 * <p>
 * This class is the Controller for DarkStore Operations
 * like Add, Update, Read and Delete the DarkStore.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/admin/darkstores")
public class DarkStoreController {

    @Autowired
    private DarkStoreService darkStoreService;

    @Autowired
    private OrderAssignService orderAssignService;

    /**
     * <p>
     * This method Add a DarkStore to the Database
     * </p>
     *
     * @param darkStoreDto {@link DarkStoreDto}- DarkStore as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addDarkStore(
            @Valid @RequestBody DarkStoreDto darkStoreDto) {
        APIResponse apiResponse = darkStoreService.addDarkStore(darkStoreDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get all the DarkStores in the Database
     * </p>
     *
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping
    public ResponseEntity<APIResponse> getDarkStores() {
        APIResponse apiResponse = darkStoreService.getDarkStores();
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get the DarkStore by DarkStore Id
     * </p>
     *
     * @param darkStoreId - To Identify DarkStore
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{darkStoreId}")
    public ResponseEntity<APIResponse> getDarkStoreById(String darkStoreId) {
        APIResponse apiResponse = darkStoreService.getDarkStoreById(darkStoreId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method Delete the Dark Store in the Database
     * with the DarkStoreId
     * </p>
     *
     * @param darkStoreId - To Identify DarkStore
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{darkStoreId}")
    public ResponseEntity<APIResponse> deleteDarkStore(String darkStoreId) {
        APIResponse apiResponse = darkStoreService.deleteDarkStore(darkStoreId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method update the DarkStore Details
     * </p>
     * @param darkStoreResponseDto {@link DarkStoreResponseDto} as Dto Object to Update
     * @return APIResponse Details like Status, Data.
     */
    @PutMapping
    public ResponseEntity<APIResponse> updateDarkStore(
            @Valid @RequestBody DarkStoreResponseDto darkStoreResponseDto) {
        APIResponse apiResponse = darkStoreService.updateDarkStore(darkStoreResponseDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <>
     * Updates the assigned order status when it is
     * ready to dispatch.
     * </>
     * @param updateOrderStatusDto - {@link UpdateOrderStatusDto} for verifying order.
     * @return APIResponse for dark-stores acknowledgement.
     */
    @PostMapping("/orders/validate")
    public ResponseEntity<APIResponse> verifyOrder(
            @Valid @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        APIResponse updateResponse = orderAssignService.updateOrderStatus(
                updateOrderStatusDto.getOrderId(), updateOrderStatusDto.getStatus());
        return ResponseEntity.status(updateResponse.getStatus())
                    .body(updateResponse);
    }

}
