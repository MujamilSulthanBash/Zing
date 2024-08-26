package com.i2i.zing.mapper;

import com.i2i.zing.dto.DarkStoreDto;
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
     * @return DarkStoreDto to display.
     */
    public static DarkStoreDto convertEntityToDto(DarkStore darkStore) {
        DarkStoreDto darkStoreDto = DarkStoreDto.builder()
                .darkStoreId(darkStore.getDarkStoreId())
                .location(darkStore.getLocation())
                .build();
        return darkStoreDto;
    }

    /**
     * <p>
     *     Converts the Dto to entity object.
     * </p>
     * @param darkStoreDto {@link DarkStoreDto} for conversion into dto.
     * @return DarkStore to display.
     */
    public static DarkStore convertDtoToEntity(DarkStoreDto darkStoreDto) {
        DarkStore darkStore = DarkStore.builder()
                .darkStoreId(darkStoreDto.getDarkStoreId())
                .location(darkStoreDto.getLocation())
                .build();
        return darkStore;
    }
}
