package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.ItemDto;
import com.i2i.zing.mapper.ItemMapper;
import com.i2i.zing.model.Item;
import com.i2i.zing.repository.ItemRepository;
import com.i2i.zing.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public APIResponse addItem(ItemDto itemDto) {
        APIResponse apiResponse = new APIResponse();
        Item item = ItemMapper.convertDtoToEntity(itemDto);
        ItemMapper.convertEntityToDto(itemRepository.save(item));
        apiResponse.setData(item);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getItems() {
        APIResponse apiResponse = new APIResponse();
        List<ItemDto> result = new ArrayList<>();
        List<Item> items = itemRepository.findByIsDeletedFalse();
        for (Item item : items) {
            result.add(ItemMapper.convertEntityToDto(item));
        }
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getItemById(String itemId) {
        APIResponse apiResponse = new APIResponse();
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        ItemMapper.convertEntityToDto(item);
        apiResponse.setData(item);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteItem(String itemId) {
        APIResponse apiResponse = new APIResponse();
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        item.setDeleted(true);
        itemRepository.save(item);
        apiResponse.setData("Item Deleted Successfully : " + itemId);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
