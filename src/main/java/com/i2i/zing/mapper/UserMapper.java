package com.i2i.zing.mapper;

import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.model.User;

public class UserMapper {
    public static User userEntity(CustomerRequestDto customerRequestDto) {
        return User.builder()
                .userName(customerRequestDto.getUserName())
                .emailId(customerRequestDto.getEmailId())
                .contactNumber(customerRequestDto.getContactNumber())
                .location(customerRequestDto.getLocation())
                .password(customerRequestDto.getPassword())
                .build();
    }

    public static CustomerRequestDto customerDto(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        return CustomerRequestDto.builder()
                .userName(deliveryPersonRequestDto.getUserName())
                .emailId(deliveryPersonRequestDto.getEmailId())
                .contactNumber(deliveryPersonRequestDto.getContactNumber())
                .location(deliveryPersonRequestDto.getLocation())
                .password(deliveryPersonRequestDto.getPassword())
                .build();
    }

    public static User userEntity(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        return User.builder()
                .userName(deliveryPersonRequestDto.getUserName())
                .emailId(deliveryPersonRequestDto.getEmailId())
                .contactNumber(deliveryPersonRequestDto.getContactNumber())
                .location(deliveryPersonRequestDto.getLocation())
                .password(deliveryPersonRequestDto.getPassword())
                .build();
    }
}
