package com.i2i.zing.repository;

import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByIsDeletedFalse();

    Item findByIsDeletedFalseAndItemId(String itemId);

    @Query(value = "select * from stocks left join darkstores on stock.darkstore_id = darkstores.id where location = :location", nativeQuery = true)
    List<Stock> findStocksByLocation(String location);
}
