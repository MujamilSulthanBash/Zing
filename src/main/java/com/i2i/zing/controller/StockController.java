package com.i2i.zing.controller;

import com.i2i.zing.dto.StockDto;
import com.i2i.zing.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return StockDto as Dto object
     */
    @PostMapping
    public ResponseEntity<StockDto> addStock(@RequestBody StockDto stockDto) {
        StockDto createdStockDto = stockService.addStock(stockDto);
        return new ResponseEntity<>(createdStockDto, HttpStatus.CREATED);
    }

    /**
     * <p>
     *     This method get all the Stock Details in the Database table
     * </p>
     * @return List of StockDto {@link StockDto}
     */
    @GetMapping
    public ResponseEntity<List<StockDto>> getStocks() {
        return new ResponseEntity<>(stockService.getStocks(), HttpStatus.OK);
    }

    /**
     * <p>
     *     This method get the Stock Details by ID
     * </p>
     * @param stockId - To Identify the Stock
     * @return StockDto {@link StockDto} as Dto Object
     */
    @GetMapping("/{stockId}")
    public ResponseEntity<StockDto> getStockById(String stockId) {
        StockDto retrievedStock = stockService.getStockById(stockId);
        return new ResponseEntity<>(retrievedStock, HttpStatus.OK);
    }

    /**
     * <p>
     *     This method delete the Stock Details by ID
     * </p>
     * @param stockId - To Identify the Stock
     * @return ResponseEntity Status OK
     */
    @DeleteMapping("/{stockId}")
    public ResponseEntity<Void> deleteStock(String stockId) {
        stockService.deleteStock(stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
