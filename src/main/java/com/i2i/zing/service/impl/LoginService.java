package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.mapper.UserMapper;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public APIResponse signUp(CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = new APIResponse();
        User user = UserMapper.userEntity(customerRequestDto);
        user.setPassword(encoder.encode(customerRequestDto.getPassword()));
        userRepository.save(user);
        customerService.createCustomer(user);
        apiResponse.setData(user);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse signUpDeliveryPerson(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = new APIResponse();
        DeliveryPerson deliveryPerson = DeliveryPerson.builder()
                .aadharNumber(deliveryPersonRequestDto.getAadharNumber())
                .licenseNumber(deliveryPersonRequestDto.getLicenseNumber())
                .vehicleNumber(deliveryPersonRequestDto.getVehicleNumber())
                .build();
        User user = UserMapper.userEntity(deliveryPersonRequestDto);
        user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
        userRepository.save(user);
        deliveryPersonService.createDeliveryPerson(deliveryPerson, user);
        apiResponse.setData(user);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
