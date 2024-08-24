package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.UserRole;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.mapper.UserMapper;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
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
        Role role = roleService.retrieveRoleByName(UserRole.CUSTOMER);
        User user = UserMapper.userEntity(customerRequestDto);
        if (! userService.checkByEmailId(customerRequestDto.getEmailId())) {
            user.setPassword(encoder.encode(customerRequestDto.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userService.createUser(user);
            customerService.createCustomer(user);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        User checkUser = userService.retrieveByEmail(customerRequestDto.getEmailId());
        boolean roleExist = false;
        for (User userCheck  : role.getUsers()) {
            if (userCheck.getUserId().equals(checkUser.getUserId())) {
                roleExist = true;
                break;
            }
        }
        if(!roleExist) {
            user.setUserId(checkUser.getUserId());
            checkUser.getRoles().add(role);
            user.setRoles(checkUser.getRoles());
            userService.createUser(user);
            customerService.createCustomer(user);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.FOUND.value());
        return apiResponse;
    }

    public APIResponse signUpDeliveryPerson(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.retrieveRoleByName(UserRole.DELIVERYPERSON);
        User user = UserMapper.userEntity(deliveryPersonRequestDto);
        if (!userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())) {
            user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userService.createUser(user);
            DeliveryPerson deliveryPerson = DeliveryPerson.builder()
                    .aadharNumber(deliveryPersonRequestDto.getAadharNumber())
                    .licenseNumber(deliveryPersonRequestDto.getLicenseNumber())
                    .vehicleNumber(deliveryPersonRequestDto.getVehicleNumber())
                    .build();
            user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
            deliveryPersonService.createDeliveryPerson(deliveryPerson,user);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        User checkUser = userService.retrieveByEmail(deliveryPersonRequestDto.getEmailId());
        boolean roleExist = false;
        for (User userCheck  : role.getUsers()) {
            if (userCheck.getUserId().equals(checkUser.getUserId())) {
                roleExist = true;
                break;
            }
        }
        if (!roleExist) {
            user.setUserId(checkUser.getUserId());
            checkUser.getRoles().add(role);
            user.setRoles(checkUser.getRoles());
            userService.createUser(user);
            DeliveryPerson deliveryPerson = DeliveryPerson.builder()
                    .aadharNumber(deliveryPersonRequestDto.getAadharNumber())
                    .licenseNumber(deliveryPersonRequestDto.getLicenseNumber())
                    .vehicleNumber(deliveryPersonRequestDto.getVehicleNumber())
                    .build();
            user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
            deliveryPersonService.createDeliveryPerson(deliveryPerson,user);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.FOUND.value());
        return apiResponse;
    }
}
