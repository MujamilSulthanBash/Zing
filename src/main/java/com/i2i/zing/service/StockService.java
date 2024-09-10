package com.i2i.zing.service;

import java.util.List;
import java.util.Set;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.StockRequestDto;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.model.Stock;

/**
 * <p>
 * This interface has Abstract methods to implements the
 * methods for Stock Operations like add, retrieve and
 * delete Stocks
 * </p>
 */
public interface StockService {

    /**
     * <p>
     * This method add the Stock to the Database
     * </p>
     *
     * @param stockRequestDto {@link StockRequestDto} - Stock Details as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    APIResponse addStock(StockRequestDto stockRequestDto);

    /**
     * <p>
     * This method get all the Details of Stock from the Database
     * </p>
     *
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getStocks();

    /**
     * <p>
     * This method get the Stock Details by the Stock ID
     * </p>
     *
     * @param stockId - To Identify the Stock
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getStockById(String stockId);

    /**
     * <p>
     * This method delete the Stock by the Stock ID
     * </p>
     *
     * @param stockId - To Identify the Stock
     * @return APIResponse Details like Status, Data.
     */
    APIResponse deleteStock(String stockId);

    /**
     * <p>
     * This method Reduce the Stock item after Order Placed
     * </p>
     *
     * @param cartItems - Set of Cart Items
     */
    void reduceStocks(Set<CartItem> cartItems);

    /**
     * <p>
     * This method update the stock Details of the Particular Stock
     * </p>
     *
     * @param stockRequestDto {@link StockRequestDto} - Stock as Dto Object
     * @return APIResponse details like status and data.
     */
    APIResponse updateStock(StockRequestDto stockRequestDto);

    /**
     * <p>
     * This method get the Stock Details by the Location
     * </p>
     *
     * @param location - Location of the User
     * @return List of Stock Details
     */
    List<Stock> getStocksByLocation(String location);

    /**
     * <p>
     * This method get the Stock Details of the Particular Item
     * by Item Id
     * </p>
     *
     * @param itemId - To Identify the Item
     * @param location - To identify the location.
     * @return Stock as Entity Object
     */
    Stock getStockByItemId(String itemId, String location);

}
