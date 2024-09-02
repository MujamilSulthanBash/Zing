package com.i2i.zing.controller;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.dto.ItemResponseDto;
import com.i2i.zing.dto.LocationRequestDto;
import com.i2i.zing.common.APIResponse;
import com.i2i.zing.service.ItemService;

/**
 * <p>
 *     This class is the Controller for Item Operations
 *     like Add, Update, Read and Delete the Items.
 * </p>
 */
@Controller
@RequestMapping("zing/api/v1/darkstores/items")
public class ItemController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    ItemService itemService;

    /**
     * <p>
     *     This method add the Item to the Database table
     * </p>
     *
     * @param itemRequestDto {@link ItemRequestDto} - Item as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addItem(@RequestBody ItemRequestDto itemRequestDto) {
        APIResponse apiResponse = itemService.addItem(itemRequestDto);
        if (null == apiResponse.getData()) {
            logger.warn("An Error Occurred while adding Item to the Database..");
        }
        logger.info("Item Added Successfully..");
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get all the Items in the database table
     * </p>
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/showitems")
    public ResponseEntity<APIResponse> getItemsByLocation(@RequestBody LocationRequestDto locationRequestDto) {
        APIResponse apiResponse = itemService.getItemsByLocation(locationRequestDto.getLocation());
        if (null == apiResponse.getData()) {
            logger.warn("No Items found in that Location..");
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get specific Item by ID given by the user
     * </p>
     * @param itemId - To Identify the Item
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<APIResponse> getItemById(@PathVariable String itemId) {
        APIResponse apiResponse = itemService.getItemById(itemId);
        if (null == apiResponse.getData()) {
            logger.warn("An Error Occurred while getting the item by ID :{}", itemId);
        }
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method delete the Item by ID
     * </p>
     * @param itemId - To Identify the Item
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<APIResponse> deleteItem(String itemId) {
        APIResponse apiResponse = itemService.deleteItem(itemId);
        if (null != apiResponse.getData()) {
            logger.warn("An Error occurred while Deleting the Item with Id : {}", itemId);
            throw new NoSuchElementException("Item Not found with Id :" + itemId);
        }
        logger.info("Dark Store deleted Successfully with Id : {}", itemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PutMapping
    public ResponseEntity<APIResponse> updateItem(@RequestBody ItemResponseDto itemResponseDto) {
        APIResponse apiResponse = itemService.updateItem(itemResponseDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}
