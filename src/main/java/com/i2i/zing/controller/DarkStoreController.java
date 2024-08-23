package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.service.DarkStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     This class is the Controller for DarkStore Operations
 *     like Add, Update, Read and Delete the DarkStore.
 * </p>
 */
@Controller
@RequestMapping("zing/api/v1/darkstores")
public class DarkStoreController {
    @Autowired
    DarkStoreService darkStoreService;

    /**
     * <p>
     *     This method Add a DarkStore to the Database
     * </p>
     * @param darkStoreDto {@link DarkStoreDto}- DarkStore as Dto Object
     * @return DarkStoreDto as Object
     */
    @PostMapping
    public ResponseEntity<APIResponse> addDarkStore(@RequestBody DarkStoreDto darkStoreDto) {
        APIResponse apiResponse = darkStoreService.addDarkStore(darkStoreDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get all the DarkStores in the Database
     * </p>
     * @return List of DarkStoreDto as Dto Objects{@link DarkStoreDto}
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
     * @return DarkStoreDto {@link DarkStoreDto}
     */
    @GetMapping("/{darkStoreId}")
    public ResponseEntity<DarkStoreDto> getDarkStoreById(String darkStoreId) {
        DarkStoreDto retrievedDarkStore = darkStoreService.getDarkStoreById(darkStoreId);
        return new ResponseEntity<>(retrievedDarkStore, HttpStatus.OK);
    }

    /**
     * <p>
     *     This method Delete the Dark Store in the Database
     *     with the DarkStoreId
     * </p>
     * @param darkStoreId - To Identify DarkStore
     * @return ResponseEntity Status OK
     */
    @DeleteMapping("/{darkStoreId}")
    public ResponseEntity<Void> deleteDarkStore(String darkStoreId) {
        darkStoreService.deleteDarkStore(darkStoreId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
