package com.i2i.zing.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.i2i.zing.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    List<Stock> findByIsDeletedFalse();

    Stock findByIsDeletedFalseAndStockId(String stockId);
}
