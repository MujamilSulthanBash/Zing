package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.i2i.zing.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    List<Stock> findByIsDeletedFalse();

    Stock findByIsDeletedFalseAndStockId(String stockId);

    @Query("SELECT s FROM Stock s LEFT JOIN FETCH s.darkstore d WHERE d.location = :location And s.quantity > 0")
    List<Stock> findStocksByLocation(String location);

    @Query("SELECT s FROM Stock s LEFT JOIN FETCH s.item i WHERE i.itemId = :itemId")
    Stock findStockByItemId(String itemId);
}
