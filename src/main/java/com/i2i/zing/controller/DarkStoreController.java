package com.i2i.zing.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.dto.DarkStoreRequestDto;
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

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    DarkStoreService darkStoreService;

    @Autowired
    OrderAssignService orderAssignService;

    /**
     * <p>
     * This method Add a DarkStore to the Database
     * </p>
     *
     * @param darkStoreDto {@link DarkStoreRequestDto}- DarkStore as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addDarkStore(@Valid @RequestBody DarkStoreDto darkStoreDto) {
        logger.debug("Dark Store Adding..");
        APIResponse apiResponse = darkStoreService.addDarkStore(darkStoreDto);
        logger.info("Dark Store Added Successfully..");
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
        logger.info("Dark Stores Retrieved Successfully..");
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
        logger.info("DarkStore Retrieved Successfully with Id :{}", darkStoreId);
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
        logger.info("Dark Store Deleted Successfully with Id :{}", darkStoreId);
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
    public ResponseEntity<APIResponse> updateDarkStore(@Valid @RequestBody DarkStoreResponseDto darkStoreResponseDto) {
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
     * @return APIResponse for darkstore's acknowledgement.
     */
    @PostMapping("/orders/validate")
    public ResponseEntity<APIResponse> verifyOrder(
            @Valid @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        APIResponse updateResponse = orderAssignService.updateOrderStatus(updateOrderStatusDto.getOrderId()
                                                 , updateOrderStatusDto.getStatus());
        return ResponseEntity.status(updateResponse.getStatus())
                    .body(updateResponse);
    }

}
