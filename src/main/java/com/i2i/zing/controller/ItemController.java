package com.i2i.zing.controller;

import com.i2i.zing.dto.ItemDto;
import com.i2i.zing.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     This class is the Controller for Item Operations
 *     like Add, Update, Read and Delete the Items.
 * </p>
 */
@Controller
@RequestMapping("zing/api/v1/items")
public class ItemController {
    @Autowired
    ItemService itemService;

    /**
     * <p>
     *     This method add the Item to the Database table
     * </p>
     * @param itemDto {@link ItemDto} - Item as Dto Object
     * @return ItemDto as Dto Object with Status
     */
    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto itemDto) {
        ItemDto createdItemDto = itemService.addItem(itemDto);
        return new ResponseEntity<>(createdItemDto, HttpStatus.CREATED);
    }

    /**
     * <p>
     *     This method get all the Items in the database table
     * </p>
     * @return List of ItemDtos {@link ItemDto}
     */
    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        return new ResponseEntity<>(itemService.getItems(), HttpStatus.OK);
    }

    /**
     * <p>
     *     This method get specific Item by ID given by the user
     * </p>
     * @param itemId - To Identify the Item
     * @return ItemDto {@link ItemDto} as Dto Object
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItemById(String itemId) {
        ItemDto retrievedItems = itemService.getItemById(itemId);
        return new ResponseEntity<>(retrievedItems, HttpStatus.OK);
    }

    /**
     * <p>
     *     This method delete the Item by ID
     * </p>
     * @param itemId - To Identify the Item
     * @return ResponseEntity status OK
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(String itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
