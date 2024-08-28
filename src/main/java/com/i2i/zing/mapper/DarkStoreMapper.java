package com.i2i.zing.mapper;

import com.i2i.zing.dto.DarkStoreRequestDto;
import com.i2i.zing.model.DarkStore;
/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class DarkStoreMapper {
    /**
     * <p>
     *     Converts the entity to dto format.
     * </p>
     * @param darkStore {@link DarkStore} into dto.
     * @return DarkStoreRequestDto to display.
     */
    public static DarkStoreRequestDto convertEntityToDto(DarkStore darkStore) {
        DarkStoreRequestDto darkStoreRequestDto = DarkStoreRequestDto.builder()
                .darkStoreId(darkStore.getDarkStoreId())
                .location(darkStore.getLocation())
                .build();
        return darkStoreRequestDto;
    }

    /**
     * <p>
     *     Converts the Dto to entity object.
     * </p>
     * @param darkStoreRequestDto {@link DarkStoreRequestDto} for conversion into dto.
     * @return DarkStore to display.
     */
    public static DarkStore convertDtoToEntity(DarkStoreRequestDto darkStoreRequestDto) {
        DarkStore darkStore = DarkStore.builder()
                .darkStoreId(darkStoreRequestDto.getDarkStoreId())
                .location(darkStoreRequestDto.getLocation())
                .build();
        return darkStore;
    }
}
