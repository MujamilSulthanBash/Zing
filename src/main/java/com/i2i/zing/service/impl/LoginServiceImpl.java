package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.UserRole;
import com.i2i.zing.configuration.JwtService;
import com.i2i.zing.dto.CustomerLoginRequest;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.mapper.UserMapper;
import com.i2i.zing.model.Customer;
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
public class LoginServiceImpl {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    private DeliveryPersonServiceImpl deliveryPersonServiceImpl;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public APIResponse signUp(CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleServiceImpl.retrieveRoleByName(UserRole.CUSTOMER);
        User user = UserMapper.userEntity(customerRequestDto);
        if (! userServiceImpl.checkByEmailId(customerRequestDto.getEmailId())) {
            user.setPassword(encoder.encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userServiceImpl.createUser(user);
            Customer customer = Customer.builder()
                    .user(user).build();
            customerServiceImpl.createCustomer(customer);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        User checkUser = userServiceImpl.retrieveByEmail(customerRequestDto.getEmailId());
        boolean roleExist = false;
        for (Role checkRole  : checkUser.getRoles()) {
            if (checkRole.getRoleId().equals(role.getRoleId())) {
                roleExist = true;
                break;
            }
        }
        if(!roleExist) {
            user.setUserId(checkUser.getUserId());
            checkUser.getRoles().add(role);
            user.setRoles(checkUser.getRoles());
            userServiceImpl.createUser(user);
            Customer customer = new Customer();
            customer.setUser(user);
            customerServiceImpl.createCustomer(customer);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.FOUND.value());
        return apiResponse;
    }

    public APIResponse customerLogIn(CustomerLoginRequest customerLoginRequest) {
        APIResponse apiResponse = new APIResponse();
        if(userServiceImpl.checkByEmailId(customerLoginRequest.getEmailId())) {
            User checkUser = userServiceImpl.retrieveByEmail(customerLoginRequest.getEmailId());
            if(encoder.matches(customerLoginRequest.getPassword(), checkUser.getPassword())) {
                String token = jwtService.generateJwt(checkUser);
                apiResponse.setData(token);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    public APIResponse signUpDeliveryPerson(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleServiceImpl.retrieveRoleByName(UserRole.DELIVERYPERSON);
        User user = UserMapper.userEntity(deliveryPersonRequestDto);
        if (!userServiceImpl.checkByEmailId(deliveryPersonRequestDto.getEmailId())) {
            user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
//            userService.createUser(user);
            DeliveryPerson deliveryPerson = DeliveryPerson.builder()
                    .aadharNumber(deliveryPersonRequestDto.getAadharNumber())
                    .licenseNumber(deliveryPersonRequestDto.getLicenseNumber())
                    .vehicleNumber(deliveryPersonRequestDto.getVehicleNumber())
                    .build();
            deliveryPerson.setUser(user);
            user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
            deliveryPersonServiceImpl.createDeliveryPerson(deliveryPerson);
            apiResponse.setData(userServiceImpl.createUser(user));
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        User checkUser = userServiceImpl.retrieveByEmail(deliveryPersonRequestDto.getEmailId());
        boolean roleExist = false;
        for (Role checkRole  : checkUser.getRoles()) {
            if (checkRole.getRoleId().equals(role.getRoleId())) {
                roleExist = true;
                break;
            }
        }
        if (!roleExist) {
            user.setUserId(checkUser.getUserId());
            checkUser.getRoles().add(role);
            user.setRoles(checkUser.getRoles());
//            userService.createUser(user);
            DeliveryPerson deliveryPerson = DeliveryPerson.builder()
                    .aadharNumber(deliveryPersonRequestDto.getAadharNumber())
                    .licenseNumber(deliveryPersonRequestDto.getLicenseNumber())
                    .vehicleNumber(deliveryPersonRequestDto.getVehicleNumber())
                    .build();
            deliveryPerson.setUser(user);
            user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
            deliveryPersonServiceImpl.createDeliveryPerson(deliveryPerson);
            apiResponse.setData(userServiceImpl.createUser(user));
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.FOUND.value());
        return apiResponse;
    }

}
