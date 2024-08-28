package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.mapper.ItemMapper;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.ItemRepository;
import com.i2i.zing.service.ItemService;
import com.i2i.zing.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private StockService stockService;

    @Override
    public APIResponse addItem(ItemRequestDto itemRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Item item = ItemMapper.convertDtoToEntity(itemRequestDto);
        ItemMapper.convertEntityToDto(itemRepository.save(item));
        apiResponse.setData(item);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getItemsByLocation(String location) {
        APIResponse apiResponse = new APIResponse();
        List<String> result = new ArrayList<>();
        List<Stock> stocks = stockService.getStocksByLocation(location);
        for (Stock stock : stocks) {
            String itemName = stock.getItem().getItemName();
            result.add(itemName);
        }
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getItemById(String itemId) {
        APIResponse apiResponse = new APIResponse();
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        apiResponse.setData(ItemMapper.convertEntityToDto(item));
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ItemRequestDto getItemDtoById(String itemId) {
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        ItemRequestDto itemRequestDto = ItemMapper.convertEntityToDto(item);
        return itemRequestDto;
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

    @Override
    public APIResponse updateItem(ItemRequestDto itemRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Item item = ItemMapper.convertDtoToEntity(itemRequestDto);
        Item existingItem = itemRepository.findByIsDeletedFalseAndItemId(itemRequestDto.getItemId());
        LocalDate modifiedDateTime = LocalDate.now();
        Date modifiedDate = Date.from(modifiedDateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        existingItem.setModifiedDate(modifiedDate);
        itemRepository.save(existingItem);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(modifiedDateTime);
        return apiResponse;
    }
}
