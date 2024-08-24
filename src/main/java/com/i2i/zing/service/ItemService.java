package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.ItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *     This interface has Abstract methods to implements the
 *     methods for Item Operations like add, retrieve and
 *     delete the Items
 * </p>
 */
@Service
public interface ItemService {
    /**
     * <p>
     *     This method add the Item to the Database
     * </p>
     * @param itemDto {@link ItemDto} - Item Details as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    APIResponse addItem(ItemDto itemDto);

    /**
     * <p>
     *     This method retrieve the items from the database
     * </p>
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getItems();

    /**
     * <p>
     *     This method retrieve the specific Item by ID
     * </p>
     * @param itemId - To Identify the Item
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getItemById(String itemId);

    /**
     * <p>
     *     This method delete the Item by ID
     * </p>
     * @param itemId - To Identify the Item
     * @return APIResponse Details like Status, Data.
     */
    APIResponse deleteItem(String itemId);
}
