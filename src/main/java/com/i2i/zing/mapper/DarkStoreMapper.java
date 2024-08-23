package com.i2i.zing.mapper;

import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.model.DarkStore;

public class DarkStoreMapper {
    public static DarkStoreDto convertEntityToDto(DarkStore darkStore) {
        DarkStoreDto darkStoreDto = DarkStoreDto.builder()
                .darkStoreId(darkStore.getDarkStoreId())
                .location(darkStore.getLocation())
                .build();
        return darkStoreDto;
    }

    public static DarkStore convertDtoToEntity(DarkStoreDto darkStoreDto) {
        DarkStore darkStore = DarkStore.builder()
                .darkStoreId(darkStoreDto.getDarkStoreId())
                .location(darkStoreDto.getLocation())
                .build();
        return darkStore;
    }
}
