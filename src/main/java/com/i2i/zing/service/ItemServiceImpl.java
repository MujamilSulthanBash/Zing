package com.i2i.zing.service;

import com.i2i.zing.dto.ItemDto;
import com.i2i.zing.mapper.ItemMapper;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public ItemDto addItem(ItemDto itemDto) {
        Item item = ItemMapper.convertDtoToEntity(itemDto);
        return ItemMapper.convertEntityToDto(itemRepository.save(item));
    }

    @Override
    public List<ItemDto> getItems() {
        List<ItemDto> result = new ArrayList<>();
        List<Item> items = itemRepository.findByIsDeletedFalse();
        for (Item item : items) {
            result.add(ItemMapper.convertEntityToDto(item));
        }
        return result;
    }

    @Override
    public ItemDto getItemById(String itemId) {
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        return ItemMapper.convertEntityToDto(item);
    }

    @Override
    public void deleteItem(String itemId) {
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        item.setDeleted(true);
        itemRepository.save(item);
    }
}
