package com.i2i.zing.service;

import com.i2i.zing.dto.StockDto;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return Stock details as Dto Object
     */
    StockDto addStock(StockDto stockDto);

    /**
     * <p>
     *     This method get all the Details of Stock from the Database
     * </p>
     * @return List of StockDto {@link StockDto} as Dto Object
     */
    List<StockDto> getStocks();

    /**
     * <p>
     *     This method get the Stock Details by the Stock ID
     * </p>
     * @param stockId - To Identify the Stock
     * @return StockDto {@link StockDto} as Dto Object
     */
    StockDto getStockById(String stockId);

    /**
     * <p>
     *     This method delete the Stock by the Stock ID
     * </p>
     * @param stockId - To Identify the Stock
     */
    void deleteStock(String stockId);
}
