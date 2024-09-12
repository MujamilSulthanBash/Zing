package com.i2i.zing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.dto.ItemUpdateDto;
import com.i2i.zing.common.APIResponse;
import com.i2i.zing.service.ItemService;

/**
 * <p>
 * This class is the Controller for Item Operations
 * like Add, Update, Read and Delete the Items.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/darkstores/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * <p>
     * This method add the Item to the Database table
     * </p>
     *
     * @param itemRequestDto {@link ItemRequestDto} - Item as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addItem(
            @Valid @RequestBody ItemRequestDto itemRequestDto) {
        APIResponse apiResponse = itemService.addItem(itemRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get all the Items in the database table
     * </p>
     *
     * @param location- Location of the DarkStore
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/show-items/{location}")
    public ResponseEntity<APIResponse> getItemsByLocation(
            @PathVariable String location) {
        APIResponse apiResponse = itemService.getItemsByLocation(location);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method get specific Item by ID given by the user
     * </p>
     *
     * @param itemId - To Identify the Item
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<APIResponse> getItemById(@PathVariable String itemId) {
        APIResponse apiResponse = itemService.getItemById(itemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method delete the Item by ID
     * </p>
     *
     * @param itemId - To Identify the Item
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<APIResponse> deleteItem(String itemId) {
        APIResponse apiResponse = itemService.deleteItem(itemId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method update the Item Details like Item Name,
     * price, Category
     * </p>
     * @param itemUpdateDto {@link ItemUpdateDto} - as Dto Object
     * @return APIResponse Details like Status, Data
     */
    @PutMapping
    public ResponseEntity<APIResponse> updateItem(
            @RequestBody ItemUpdateDto itemUpdateDto) {
        APIResponse apiResponse = itemService.updateItem(itemUpdateDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
