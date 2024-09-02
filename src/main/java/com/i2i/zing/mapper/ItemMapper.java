package com.i2i.zing.mapper;

import com.i2i.zing.dto.ItemCreationDto;
import com.i2i.zing.dto.ItemDisplayResponseDto;
import com.i2i.zing.dto.ItemRequestDto;
import com.i2i.zing.dto.ItemUpdateDto;
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
     * @return ItemRequestDto to display.
     */
    public static ItemCreationDto convertEntityToDto(Item item) {
        ItemCreationDto itemCreationDto = ItemCreationDto.builder()
                .itemId(item.getItemId())
                .name(item.getItemName())
                .price(item.getPrice())
                .categoryName(item.getCategory().getName())
                .build();
        return itemCreationDto;
    }

    /**
     * <p>
     *     This method convert the Entity Object to Response Dto
     * </p>
     * @param item - Item as Entity Object
     * @return ItemResponseDto {@link ItemUpdateDto} - as Dto Object
     */
    public static ItemUpdateDto convertEntityToResponseDto(Item item) {
        ItemUpdateDto itemUpdateDto = ItemUpdateDto.builder()
                .itemId(item.getItemId())
                .name(item.getItemName())
                .price(item.getPrice())
                .categoryName(item.getCategory().getName())
                .build();
        return itemUpdateDto;
    }

    /**
     * <p>
     *     This method convert the Entity to Displayable Response Dto
     * </p>
     * @param item - Item as entity Object
     * @return ItemDisplayResponseDto {@link ItemDisplayResponseDto} - as Dto Object
     */
    public static ItemDisplayResponseDto convertEntityToDisplayResponseDto(Item item) {
        ItemDisplayResponseDto itemDisplayResponseDto = ItemDisplayResponseDto.builder()
                .name(item.getItemName())
                .price(item.getPrice())
                .categoryName(item.getCategory().getName())
                .build();
        return itemDisplayResponseDto;
    }

    /**
     * <p>
     *     Converts the dto to entity Object
     * </p>
     * @param itemRequestDto {@link ItemRequestDto} convert to entity.
     * @return Item to display.
     */
    public static Item convertDtoToEntity(ItemRequestDto itemRequestDto) {
        Item item = Item.builder()
                .itemName(itemRequestDto.getName())
                .price(itemRequestDto.getPrice())
                .category(Category.builder()
                        .categoryId(itemRequestDto.getCategoryId())
                        .build())
                .build();
        return item;
    }

    /**
     * <p>
     *     This method convert the Dto Object to Entity as Response
     * </p>
     * @param itemUpdateDto {@link ItemUpdateDto} - as Dto Object
     * @return Item as Entity Object
     */
    public static Item convertDtoToResponseEntity(ItemUpdateDto itemUpdateDto) {
        Item item = Item.builder()
                .itemName(itemUpdateDto.getName())
                .price(itemUpdateDto.getPrice())
                .category(Category.builder()
                        .name(itemUpdateDto.getCategoryName())
                        .build())
                .build();
        return item;
    }
}
