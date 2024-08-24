package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.StockDto;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     This interface has Abstract methods to implements the
 *     methods for Stock Operations like add, retrieve and
 *     delete Stocks
 * </p>
 */
@Service
public interface StockService {

    /**
     * <p>
     *     This method add the Stock to the Database
     * </p>
     * @param stockDto {@link StockDto} - Stock Details as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    APIResponse addStock(StockDto stockDto);

    /**
     * <p>
     *     This method get all the Details of Stock from the Database
     * </p>
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getStocks();

    /**
     * <p>
     *     This method get the Stock Details by the Stock ID
     * </p>
     * @param stockId - To Identify the Stock
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getStockById(String stockId);

    /**
     * <p>
     *     This method delete the Stock by the Stock ID
     * </p>
     * @param stockId - To Identify the Stock
     * @return APIResponse Details like Status, Data.
     */
    APIResponse deleteStock(String stockId);
}
