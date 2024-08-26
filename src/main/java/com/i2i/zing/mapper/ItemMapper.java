package com.i2i.zing.mapper;

import com.i2i.zing.dto.ItemDto;
import com.i2i.zing.model.Category;
import com.i2i.zing.model.Item;
/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class ItemMapper {
    /**
     * <p>
     *     Converts the entity to dto object.
     * </p>
     * @param item {@link Item} for conversion into dto.
     * @return ItemDto to display.
     */
    public static ItemDto convertEntityToDto(Item item) {
        ItemDto itemDto = ItemDto.builder()
                .itemId(item.getItemId())
                .name(item.getItemName())
                .price(item.getPrice())
                .categoryName(item.getCategory().getName())
                .build();
        return itemDto;
    }

    /**
     * <p>
     *     Converts the dto to entity Object
     * </p>
     * @param itemDto {@link ItemDto} convert to entity.
     * @return Item to display.
     */
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
