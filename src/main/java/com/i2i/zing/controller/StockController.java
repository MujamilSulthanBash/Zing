package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.StockDto;
import com.i2i.zing.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     This class is the Controller for Stock Operations
 *     like Add, Update, Read and Delete the Stocks.
 * </p>
 */
@Controller
@RequestMapping("zing/api/v1/stocks")
public class StockController {
    @Autowired
    StockService stockService;

    /**
     * <p>
     *     This method add the Stock to the Database
     * </p>
     * @param stockDto {@link StockDto} Stock as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addStock(@RequestBody StockDto stockDto) {
        APIResponse apiResponse = stockService.addStock(stockDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get all the Stock Details in the Database table
     * </p>
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping
    public ResponseEntity<APIResponse> getStocks() {
        APIResponse apiResponse = stockService.getStocks();
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method get the Stock Details by ID
     * </p>
     * @param stockId - To Identify the Stock
     * @return APIResponse Details like Status, Data.
     */
    @GetMapping("/{stockId}")
    public ResponseEntity<APIResponse> getStockById(String stockId) {
        APIResponse apiResponse = stockService.getStockById(stockId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method delete the Stock Details by ID
     * </p>
     * @param stockId - To Identify the Stock
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{stockId}")
    public ResponseEntity<APIResponse> deleteStock(String stockId) {
        APIResponse apiResponse = stockService.deleteStock(stockId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
