package com.i2i.zing.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.dto.DarkStoreRequestDto;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.model.User;

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
        return DarkStoreRequestDto.builder()
                .darkStoreId(darkStore.getDarkStoreId())
                .build();
    }

    public static DarkStore convertDtoToEntity(DarkStoreDto darkStoreDto) {
        return DarkStore.builder()
                .build();
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
