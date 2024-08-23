package com.i2i.zing.service;

import com.i2i.zing.dto.ItemDto;

import java.util.List;

/**
 * <p>
 *     This interface has Abstract methods to implements the
 *     methods for Item Operations like add, retrieve and
 *     delete the Items
 * </p>
 */
public interface ItemService {
    /**
     * <p>
     *     This method add the Item to the Database
     * </p>
     * @param itemDto {@link ItemDto} - Item Details as Dto Object
     * @return ItemDto as Dto Object
     */
    ItemDto addItem(ItemDto itemDto);

    /**
     * <p>
     *     This method retrieve the items from the database
     * </p>
     * @return List of ItemDtos {@link ItemDto}
     */
    List<ItemDto> getItems();

    /**
     * <p>
     *     This method retrieve the specific Item by ID
     * </p>
     * @param itemId - To Identify the Item
     * @return Item Details {@link ItemDto} as Dto Object
     */
    ItemDto getItemById(String itemId);

    /**
     * <p>
     *     This method delete the Item by ID
     * </p>
     * @param itemId - To Identify the Item
     */
    void deleteItem(String itemId);
}
