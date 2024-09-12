package com.i2i.zing.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.StockRequestDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.service.DarkStoreService;
import com.i2i.zing.service.ItemService;
import com.i2i.zing.service.StockService;

/**
 * <p>
 * This class is the Controller for Stock Operations
 * like Add, Update, Read and Delete the Stocks.
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/darkstores/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private DarkStoreService darkStoreService;

    private static final Logger logger = LogManager.getLogger();

    /**
     * <p>
     * This method add the Stock to the Database
     * </p>
     *
     * @param stockRequestDto {@link StockRequestDto} Stock as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    @PostMapping
    public ResponseEntity<APIResponse> addStock(
            @Valid @RequestBody StockRequestDto stockRequestDto) {
        if ((itemService.verifyItemId(stockRequestDto.getItemId())) &&
                (darkStoreService.verifyDarkStoreId(stockRequestDto.getDarkStoreId()))) {
            APIResponse apiResponse = stockService.addStock(stockRequestDto);
            return ResponseEntity.status(apiResponse.getStatus())
                    .body(apiResponse);
        }
        logger.warn("Either Item Id : {} or Darkstore Id : {} does not exists.",
                stockRequestDto.getItemId(), stockRequestDto.getDarkStoreId());
        throw new EntityNotFoundException("Either Item Id : " + stockRequestDto.getItemId() +
                " or DarkStoreId : " + stockRequestDto.getDarkStoreId() + " does not exists.");
    }

    /**
     * <p>
     * This method get all the Stock Details in the Database table
     * </p>
     *
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
     * This method get the Stock Details by ID
     * </p>
     *
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
     * This method delete the Stock Details by ID
     * </p>
     *
     * @param stockId - To Identify the Stock
     * @return APIResponse Details like Status, Data.
     */
    @DeleteMapping("/{stockId}")
    public ResponseEntity<APIResponse> deleteStock(String stockId) {
        APIResponse apiResponse = stockService.deleteStock(stockId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method Update the Stock Details by Stock Id
     * </p>
     *
     * @param stockRequestDto {@link StockRequestDto} - Stock as Dto Object
     * @return - APIResponse Details like Status, Data.
     */
    @PutMapping
    public ResponseEntity<APIResponse> updateStock(
            @Valid @RequestBody StockRequestDto stockRequestDto) {
        APIResponse apiResponse = stockService.updateStock(stockRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
