package com.i2i.zing.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.UserRole;
import com.i2i.zing.configuration.JwtService;
import com.i2i.zing.dto.CustomerLoginRequestDto;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonLoginRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.mapper.UserMapper;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.service.CustomerService;
import com.i2i.zing.service.DeliveryPersonService;
import com.i2i.zing.service.LoginService;
import com.i2i.zing.service.RoleService;
import com.i2i.zing.service.UserService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public APIResponse customerSignUp(CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.retrieveRoleByName(UserRole.CUSTOMER);
        User user = UserMapper.userEntity(customerRequestDto);
        if (userService.checkByEmailId(customerRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(customerRequestDto.getEmailId());
            boolean checkRole = checkRole(checkUser.getRoles(), role.getRoleId());
            if (!checkRole) {
                Set<Role> roles = checkUser.getRoles();
                roles.add(role);
                user.setRoles(roles);
                user.setUserId(checkUser.getUserId());
                User savedUser = createUser(customerRequestDto);
                createCustomer(customerRequestDto, savedUser);
                apiResponse.setData(savedUser);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        User savedUser = createUser(customerRequestDto);
        createCustomer(customerRequestDto, savedUser);
        apiResponse.setData(savedUser.getRoles());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse customerLogin(CustomerLoginRequestDto customerLoginRequestDto) {
        APIResponse apiResponse = new APIResponse();
        if (userService.checkByEmailId(customerLoginRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(customerLoginRequestDto.getEmailId());
            if (encoder.matches(customerLoginRequestDto.getPassword(), checkUser.getPassword())) {
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


    public APIResponse deliveryPersonSignup(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.retrieveRoleByName(UserRole.DELIVERYPERSON);
        User user = UserMapper.userEntity(deliveryPersonRequestDto);
        if (userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(deliveryPersonRequestDto.getEmailId());
            boolean checkRole = checkRole(checkUser.getRoles(), role.getRoleId());
            if (!checkRole) {
                Set<Role> roles = checkUser.getRoles();
                roles.add(role);
                user.setRoles(roles);
                user.setUserId(checkUser.getUserId());
                User savedUser = userService.createUser(user);
                createDeliveryPerson(deliveryPersonRequestDto, savedUser);
                apiResponse.setData(savedUser);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        User savedUser = userService.createUser(user);
        createDeliveryPerson(deliveryPersonRequestDto, savedUser);
        apiResponse.setData(savedUser);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deliveryPersonLogin(DeliveryPersonLoginRequestDto deliveryPersonLoginRequestDto) {
        APIResponse apiResponse = new APIResponse();
        if (userService.checkByEmailId(deliveryPersonLoginRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(deliveryPersonLoginRequestDto.getEmailId());
            if (encoder.matches(deliveryPersonLoginRequestDto.getPassword(), checkUser.getPassword())) {
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

    private void createCustomer(CustomerRequestDto customerRequestDto, User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        customerService.createCustomer(customer);
    }

    private void createDeliveryPerson(DeliveryPersonRequestDto deliveryPersonRequestDto, User user) {
        DeliveryPerson deliveryPerson = DeliveryPerson.builder()
                .aadharNumber(deliveryPersonRequestDto.getAadharNumber())
                .licenseNumber(deliveryPersonRequestDto.getLicenseNumber())
                .vehicleNumber(deliveryPersonRequestDto.getVehicleNumber())
                .build();
        deliveryPerson.setUser(user);
        deliveryPersonService.createDeliveryPerson(deliveryPerson);
    }

    private User createUser(CustomerRequestDto customerRequestDto) {
        Role role = roleService.retrieveRoleByName(UserRole.CUSTOMER);
        User user = UserMapper.userEntity(customerRequestDto);
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userService.createUser(user);
    }

    private boolean checkRole(Set<Role> roles, String roleId) {
        for (Role checkRole : roles) {
            if (checkRole.getRoleId().equals(roleId)) {
                return true;
            }
        }
        return false;
    }

}
