package com.i2i.zing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.dto.ItemDisplayResponseDto;
import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.dto.ItemResponseDto;
import com.i2i.zing.mapper.ItemMapper;
import com.i2i.zing.model.Item;
import com.i2i.zing.model.Stock;
import com.i2i.zing.repository.ItemRepository;
import com.i2i.zing.service.ItemService;
import com.i2i.zing.service.StockService;

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
        apiResponse.setData(ItemMapper.convertEntityToDto(itemRepository.save(item)));
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getItemsByLocation(String location) {
        APIResponse apiResponse = new APIResponse();
        Map<String, ItemDisplayResponseDto> items = new HashMap<>();
        List<Stock> stocks = stockService.getStocksByLocation(location);
        for (Stock stock : stocks) {
            ItemDisplayResponseDto itemDto = ItemMapper.convertEntityToDisplayResponseDto(stock.getItem());
            itemDto.setQuantity(stock.getQuantity());
            if (itemDto.getQuantity() <= 5) {
                itemDto.setStatus("Limited Stock :" + itemDto.getQuantity());
            }
            if (itemDto.getQuantity() > 5) {
                itemDto.setStatus("In Stock.");
            }
            items.put(stock.getItem().getItemName(), itemDto);
        }
        apiResponse.setData(items);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getItemById(String itemId) {
        APIResponse apiResponse = new APIResponse();
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        apiResponse.setData(ItemMapper.convertEntityToResponseDto(item));
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ItemResponseDto getItemDtoById(String itemId) {
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        ItemResponseDto itemResponseDto = ItemMapper.convertEntityToResponseDto(item);
        return itemResponseDto;
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
    public APIResponse updateItem(ItemResponseDto itemResponseDto) {
        APIResponse apiResponse = new APIResponse();
        Item item = ItemMapper.convertDtoToResponseEntity(itemResponseDto);
        Item existingItem = itemRepository.findByIsDeletedFalseAndItemId(itemResponseDto.getItemId());
        LocalDate modifiedDateTime = LocalDate.now();
        Date modifiedDate = Date.from(modifiedDateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        existingItem.setModifiedDate(modifiedDate);
        itemRepository.save(existingItem);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(modifiedDateTime);
        return apiResponse;
    }
}
