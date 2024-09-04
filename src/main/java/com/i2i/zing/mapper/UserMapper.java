package com.i2i.zing.mapper;

import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.model.User;

/**
 * <p>
 * Converts the json objects according to application operations.
 * e.g., (dto object -> entity object, entity object -> dto object)
 * </p>
 */
public class UserMapper {

    /**
     * <p>
     *     This method convert the CustomerRequestDto
     *     to User Entity Object
     * </p>
     * @param customerRequestDto  {@link CustomerRequestDto} - Customer Request as Dto
     * @return User as Entity Object
     */
    public static User userEntity(CustomerRequestDto customerRequestDto) {
        return User.builder()
                .userName(customerRequestDto.getUserName())
                .emailId(customerRequestDto.getEmailId())
                .contactNumber(customerRequestDto.getContactNumber())
                .location(customerRequestDto.getLocation())
                .password(customerRequestDto.getPassword())
                .build();
    }

    /**
     * <p>
     *     This class convert the Dto to Entity Object
     * </p>
     * @param deliveryPersonRequestDto {@link DeliveryPersonRequestDto} as Dto Object
     * @return CustomerRequestDto as Dto Object
     */
    public static CustomerRequestDto customerDto(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        return CustomerRequestDto.builder()
                .userName(deliveryPersonRequestDto.getUserName())
                .emailId(deliveryPersonRequestDto.getEmailId())
                .contactNumber(deliveryPersonRequestDto.getContactNumber())
                .location(deliveryPersonRequestDto.getLocation())
                .password(deliveryPersonRequestDto.getPassword())
                .build();
    }

    /**
     * <p>
     *     This method convert the Dto to Entity Object
     * </p>
     * @param deliveryPersonRequestDto {@link DeliveryPersonRequestDto} as Dto Object
     * @return User as Entity Object
     */
    public static User getUserEntityFromDeliveryPerson(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        return User.builder()
                .userName(deliveryPersonRequestDto.getUserName())
                .emailId(deliveryPersonRequestDto.getEmailId())
                .contactNumber(deliveryPersonRequestDto.getContactNumber())
                .location(deliveryPersonRequestDto.getLocation())
                .password(deliveryPersonRequestDto.getPassword())
                .build();
    }

}
