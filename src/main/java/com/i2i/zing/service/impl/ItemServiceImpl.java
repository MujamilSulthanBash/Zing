package com.i2i.zing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.ZoneId;

import com.i2i.zing.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger();

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
        if (null == apiResponse.getData()) {
            logger.warn("An error Occurred while Adding Item..");
        }
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
        if (stocks.isEmpty()) {
            logger.warn("Stock List is Empty..");
        }
        return apiResponse;
    }

    @Override
    public APIResponse getItemById(String itemId) {
        APIResponse apiResponse = new APIResponse();
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        apiResponse.setData(ItemMapper.convertEntityToResponseDto(item));
        apiResponse.setStatus(HttpStatus.OK.value());
        if (null == apiResponse.getData()) {
            logger.warn("An Error Occurred while getting Item with Id : {} not found.", itemId);
            throw new EntityNotFoundException("Item Not found with Id : " + itemId);
        }
        return apiResponse;
    }

    @Override
    public ItemResponseDto getItemDtoById(String itemId) {
        Item item = itemRepository.findByIsDeletedFalseAndItemId(itemId);
        ItemResponseDto itemResponseDto = ItemMapper.convertEntityToResponseDto(item);
        if (null == itemResponseDto) {
            logger.warn("An Error Occurred while getting Item with Id : {}", itemId);
            throw new EntityNotFoundException("Item Not found with Id : " + itemId);
        }
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
