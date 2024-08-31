package com.i2i.zing.controller;

import com.i2i.zing.dto.DarkStoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreRequestDto;
import com.i2i.zing.service.DarkStoreService;
/**
 * <p>
 *     This class is the Controller for DarkStore Operations
 *     like Add, Update, Read and Delete the DarkStore.
 * </p>
 */
@Controller
@RequestMapping("zing/api/v1/admin/darkstores")
public class DarkStoreController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    DarkStoreService darkStoreService;

    /**
     * <p>
     *     This method Add a DarkStore to the Database
     * </p>
     * @param darkStoreDto {@link DarkStoreRequestDto}- DarkStore as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addDarkStore(@RequestBody DarkStoreDto darkStoreDto) {
        logger.debug("Dark Store Adding..");
        APIResponse apiResponse = darkStoreService.addDarkStore(darkStoreDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get all the DarkStores in the Database
     * </p>
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
     *     This method get the DarkStore by DarkStore Id
     * </p>
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
     *     This method Delete the Dark Store in the Database
     *     with the DarkStoreId
     * </p>
     * @param darkStoreId - To Identify DarkStore
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{darkStoreId}")
    public ResponseEntity<APIResponse> deleteDarkStore(String darkStoreId) {
        APIResponse apiResponse = darkStoreService.deleteDarkStore(darkStoreId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
