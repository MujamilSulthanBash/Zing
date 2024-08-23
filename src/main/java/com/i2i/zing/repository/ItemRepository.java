package com.i2i.zing.repository;

import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByIsDeletedFalse();

    Item findByIsDeletedFalseAndItemId(String itemId);
}
