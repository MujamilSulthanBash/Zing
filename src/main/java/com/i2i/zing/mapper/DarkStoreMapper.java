package com.i2i.zing.mapper;

import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.dto.DarkStoreRequestDto;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * Converts the json objects according to application operations.
 * e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class DarkStoreMapper {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * <p>
     * Converts the entity to dto format.
     * </p>
     *
     * @param darkStore {@link DarkStore} into dto.
     * @return DarkStoreRequestDto to display.
     */
    public static DarkStoreRequestDto convertEntityToDto(DarkStore darkStore) {
        DarkStoreRequestDto darkStoreRequestDto = DarkStoreRequestDto.builder()
                .darkStoreId(darkStore.getDarkStoreId())
                .build();
        return darkStoreRequestDto;
    }

    /**
     * <p>
     * Converts the Dto to entity object.
     * </p>
     *
     * @param darkStoreRequestDto {@link DarkStoreRequestDto} for conversion into dto.
     * @return DarkStore to display.
     */
    public static DarkStore convertDtoToEntity(DarkStoreRequestDto darkStoreRequestDto) {
        DarkStore darkStore = DarkStore.builder()
                .darkStoreId(darkStoreRequestDto.getDarkStoreId())
                .build();
        return darkStore;
    }

    public static DarkStore convertDtoToEntity(DarkStoreDto darkStoreDto) {
        DarkStore darkStore = DarkStore.builder()
                .build();
        return darkStore;
    }

    public static User convertDtoToUser(DarkStoreDto darkStoreDto) {
        return User.builder()
                .userName(darkStoreDto.getUserName())
                .contactNumber(darkStoreDto.getContactNumber())
                .location(darkStoreDto.getLocation())
                .emailId(darkStoreDto.getEmailId())
                .password(encoder.encode(darkStoreDto.getPassword()))
                .build();
    }

}
