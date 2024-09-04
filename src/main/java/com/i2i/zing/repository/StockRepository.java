package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.i2i.zing.model.Stock;

/**
 * <p>
 * This interface has Abstract methods for
 * Stock Operations like get all the Stock , get the Stock
 * By Id, get the Stocks by the Location
 * find the Stock by the item ID
 * </p>
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    /**
     * <p>
     * This method get the List of Stocks in the
     * Database if the Stock is not Deleted.
     * </p>
     *
     * @return List of Stocks
     */
    List<Stock> findByIsDeletedFalse();

    /**
     * <p>
     * This method get the Stock by Stock Id
     * </p>
     *
     * @param stockId - To Identify the Stock
     * @return Stock as Entity Object
     */
    Stock findByIsDeletedFalseAndStockId(String stockId);

    /**
     * <p>
     * This method get all the Stocks by the
     * Location
     * </p>
     *
     * @param location - To Identify the Stock by the Location based
     * @return List of Stock by the Location
     */
    @Query("SELECT s FROM Stock s LEFT JOIN FETCH s.darkstore d JOIN FETCH d.user u WHERE u.location = :location And s.quantity > 0")
    List<Stock> findStocksByLocation(String location);

    /**
     * <p>
     * This method get the Stock by Item Id
     * </p>
     *
     * @param itemId - To Identify the Item
     * @return Stock as Entity Object
     */
    @Query("SELECT s FROM Stock s LEFT JOIN FETCH s.item i WHERE i.itemId = :itemId")
    Stock findStockByItemId(String itemId);
}
