package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.i2i.zing.model.Item;

public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> findByIsDeletedFalse();

    Item findByIsDeletedFalseAndItemId(String itemId);

}
