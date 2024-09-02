package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.dto.ItemUpdateDto;
import org.springframework.stereotype.Service;

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
     * @param itemRequestDto {@link ItemRequestDto} - Item Details as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    APIResponse addItem(ItemRequestDto itemRequestDto);

    /**
     * <p>
     *     This method retrieve the items from the database
     * </p>
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getItemsByLocation(String location);

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
     *     This method retrieve the specific ItemRequestDto by ID for internal operation,
     * </p>
     * @param itemId - To Identify the Item
     * @return APIResponse Details like Status, Data.
     */
    ItemUpdateDto getItemDtoById(String itemId);

    /**
     * <p>
     *     This method delete the Item by ID
     * </p>
     * @param itemId - To Identify the Item
     * @return APIResponse Details like Status, Data.
     */
    APIResponse deleteItem(String itemId);

    /**
     * <p>
     *     This method Update the Item Details
     * </p>
     * @param itemUpdateDto {@link ItemUpdateDto} - As Dto Object
     * @return APIResponse Details Like Status, Data.
     */
    APIResponse updateItem(ItemUpdateDto itemUpdateDto);
}
