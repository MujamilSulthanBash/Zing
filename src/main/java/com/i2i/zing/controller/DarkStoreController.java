package com.i2i.zing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
     *
     * @param darkStoreRequestDto {@link DarkStoreRequestDto}- DarkStore as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addDarkStore(@RequestBody DarkStoreRequestDto darkStoreRequestDto) {
        APIResponse apiResponse = darkStoreService.addDarkStore(darkStoreRequestDto);
        if (null == apiResponse.getData()) {
            logger.warn("An Error Occurred while adding Dark Store to the Database..");
        }
        logger.info("Dark Store Created Successfully..");
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get all the DarkStores in the Database
     * </p>
     *
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping
    public ResponseEntity<APIResponse> getDarkStores() {
        APIResponse apiResponse = darkStoreService.getDarkStores();
        if (null == apiResponse.getData()) {
            logger.warn("No DarkStores Found..");
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get the DarkStore by DarkStore Id
     * </p>
     *
     * @param darkStoreId - To Identify DarkStore
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{darkStoreId}")
    public ResponseEntity<APIResponse> getDarkStoreById(String darkStoreId) {
        APIResponse apiResponse = darkStoreService.getDarkStoreById(darkStoreId);
        if (null == apiResponse.getData()) {
            logger.warn("No Dark Store found with Id : {}", darkStoreId);
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method Delete the Dark Store in the Database
     *     with the DarkStoreId
     * </p>
     *
     * @param darkStoreId - To Identify DarkStore
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{darkStoreId}")
    public ResponseEntity<APIResponse> deleteDarkStore(String darkStoreId) {
        APIResponse apiResponse = darkStoreService.deleteDarkStore(darkStoreId);
        if (null != apiResponse.getData()) {
            logger.warn("An Error occurred while Deleting the DarkStore with Id : {}", darkStoreId);
        }
        logger.info("Dark Store deleted Successfully with Id : {}", darkStoreId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
