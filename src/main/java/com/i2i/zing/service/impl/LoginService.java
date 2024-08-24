package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.UserRole;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.mapper.UserMapper;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public APIResponse signUp(CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = new APIResponse();
        User checkUser = userService.retrieveByEmail(customerRequestDto.getEmailId());
        if (userService.checkByEmailId(customerRequestDto.getEmailId())) {
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        User user = UserMapper.userEntity(customerRequestDto);
        user.setPassword(encoder.encode(customerRequestDto.getPassword()));
        Role role = roleService.retrieveRoleByName(UserRole.CUSTOMER);
        if (user.getRoles() == null)
        {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            user.getRoles().add(role);
        }
        userService.createUser(user);
        customerService.createCustomer(user);
        apiResponse.setData(user);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse signUpDeliveryPerson(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = new APIResponse();
        if (userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())) {
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        DeliveryPerson deliveryPerson = DeliveryPerson.builder()
                .aadharNumber(deliveryPersonRequestDto.getAadharNumber())
                .licenseNumber(deliveryPersonRequestDto.getLicenseNumber())
                .vehicleNumber(deliveryPersonRequestDto.getVehicleNumber())
                .build();
        User user = UserMapper.userEntity(deliveryPersonRequestDto);
        user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
        Role role = roleService.retrieveRoleByName(UserRole.DELIVERYPERSON);
        if (user.getRoles().isEmpty())
        {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            user.getRoles().add(role);
        }
        userService.createUser(user);
        deliveryPersonService.createDeliveryPerson(deliveryPerson, user);
        apiResponse.setData(user);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

}
