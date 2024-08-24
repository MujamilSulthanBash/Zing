package com.i2i.zing.mapper;

import com.i2i.zing.dto.ItemDto;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;

public class ItemMapper {
    public static ItemDto convertEntityToDto(Item item) {
        ItemDto itemDto = ItemDto.builder()
                .itemId(item.getItemId())
                .name(item.getItemName())
                .price(item.getPrice())
                .categoryName(item.getCategory().getName())
                .build();
        return itemDto;
    }

    public static Item convertDtoToEntity(ItemDto itemDto) {
        Item item = Item.builder()
                .itemId(itemDto.getItemId())
                .itemName(itemDto.getName())
                .price(itemDto.getPrice())
                .category(Category.builder()
                        .categoryId(itemDto.getCategoryId())
                        .build())
                .build();
        return item;
    }
}
