package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.i2i.zing.model.Item;

/**
 * <p>
 *     This interface has Abstract methods for Item Operations
 *     like Get all the Items and Item Object
 * </p>
 */
public interface ItemRepository extends JpaRepository<Item, String> {

    /**
     * <p>
     *     This method get the List of Items if the
     *     Items are Not Deleted
     * </p>
     * @return List of Items
     */
    List<Item> findByIsDeletedFalse();

    /**
     * <p>
     *     This method get the Item as Object
     *     By item ID
     * </p>
     * @param itemId - To Identify the Item
     * @return Item as Entity Object
     */
    Item findByIsDeletedFalseAndItemId(String itemId);

}
