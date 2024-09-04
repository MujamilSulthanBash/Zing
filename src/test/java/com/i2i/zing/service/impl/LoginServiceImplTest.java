package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.configuration.JwtService;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.dto.UserLoginRequestDto;
import com.i2i.zing.dto.VerifyEmailDto;
import com.i2i.zing.mapper.UserMapperTest;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoginServiceImplTest {

    @Mock
    UserService userService;

    @Mock
    CustomerService customerService;

    @Mock
    RoleService roleService;

    @Mock
    DeliveryPersonService deliveryPersonService;

    @InjectMocks
    LoginServiceImpl loginService;

    @Mock
    EmailSenderService emailSenderService;

    @Mock
    JwtService jwtService;

    private Role role;
    private Role deliverPersonRole;
    private User user;
    private CustomerRequestDto customerRequestDto;
    private User savedUser;
    private User userWithRole;
    private User userWithSameRole;
    private User savedUserForDeliveryPerson;
    private DeliveryPersonRequestDto deliveryPersonRequestDto;
    private User deliveryPersonWithRole;
    private User deliveryPersonWithSameRole;
    private UserLoginRequestDto userLoginRequestDto;
    private UserLoginRequestDto userLoginRequestDtoSuccess;
    private VerifyEmailDto verifyEmailDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = UserMapperTest.getCustomerRole();
        user = UserMapperTest.getUser();
        savedUser = UserMapperTest.getSavedUser();
        userWithRole = UserMapperTest.getExistingUserWithRole();
        userWithSameRole = UserMapperTest.getExistingUserWithSameRole();
        customerRequestDto = UserMapperTest.getCustomerRequestDto();
        deliverPersonRole = UserMapperTest.getDeliveryPersonRole();
        savedUserForDeliveryPerson = UserMapperTest.getSavedUserForDeliveryPerson();
        deliveryPersonRequestDto = UserMapperTest.getDeliveryPersonRequestDto();
        deliveryPersonWithRole = UserMapperTest.getUserWithRole();
        deliveryPersonWithSameRole = UserMapperTest.getUserWithSameRole();
        userLoginRequestDto = UserMapperTest.getUserLoginRequestDto();
        userLoginRequestDtoSuccess = UserMapperTest.getUserLoginRequestDtoSuccess();
        verifyEmailDto = UserMapperTest.getVerifyEmailDto();
    }

    @Test
    void testCustomerSignUp_ExistingUser() {
        when(roleService.retrieveRoleByName(any())).thenReturn(role);
        when(userService.checkByEmailId(customerRequestDto.getEmailId())).thenReturn(false);
        when(userService.retrieveByEmail(customerRequestDto.getEmailId())).thenReturn(savedUser);
        when(userService.createUser(user)).thenReturn(savedUser);
        APIResponse apiResponse = loginService.customerSignUp(customerRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testDeliverPersonSignUp_ExistingUser() {
        when(roleService.retrieveRoleByName(any())).thenReturn(deliverPersonRole);
        when(userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())).thenReturn(false);
        when(userService.retrieveByEmail(deliveryPersonRequestDto.getEmailId())).thenReturn(savedUserForDeliveryPerson);
        when(userService.createUser(user)).thenReturn(savedUserForDeliveryPerson);
        APIResponse apiResponse = loginService.deliveryPersonSignup(deliveryPersonRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testCustomerSignUp_NewUser() {
        when(roleService.retrieveRoleByName(any())).thenReturn(role);
        when(userService.checkByEmailId(customerRequestDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(customerRequestDto.getEmailId())).thenReturn(savedUser);
        when(userService.createUser(user)).thenReturn(savedUser);
        emailSenderService.sendEmail(customerRequestDto.getEmailId(),"For Checking", "1234");
        APIResponse apiResponse = loginService.customerSignUp(customerRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testDeliveryPersonSignUp_NewUser() {
        when(roleService.retrieveRoleByName(any())).thenReturn(deliverPersonRole);
        when(userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(deliveryPersonRequestDto.getEmailId())).thenReturn(savedUserForDeliveryPerson);
        when(userService.createUser(user)).thenReturn(savedUserForDeliveryPerson);
        emailSenderService.sendEmail(deliveryPersonRequestDto.getEmailId(),"For Checking", "1234");
        APIResponse apiResponse = loginService.deliveryPersonSignup(deliveryPersonRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testCustomerSignUp_NewCustomerWithExistingRole() {
        when(roleService.retrieveRoleByName(any())).thenReturn(role);
        when(userService.checkByEmailId(customerRequestDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(customerRequestDto.getEmailId())).thenReturn(userWithRole);
        when(userService.createUser(user)).thenReturn(savedUser);
        APIResponse apiResponse = loginService.customerSignUp(customerRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testDeliveryPersonSignUp_NewCustomerWithExistingRole() {
        when(roleService.retrieveRoleByName(any())).thenReturn(deliverPersonRole);
        when(userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(deliveryPersonRequestDto.getEmailId())).thenReturn(deliveryPersonWithRole);
        when(userService.createUser(user)).thenReturn(savedUserForDeliveryPerson);
        APIResponse apiResponse = loginService.deliveryPersonSignup(deliveryPersonRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testCustomerSignUp_NewCustomerWithSameRole() {
        when(roleService.retrieveRoleByName(any())).thenReturn(role);
        when(userService.checkByEmailId(customerRequestDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(customerRequestDto.getEmailId())).thenReturn(userWithSameRole);
        when(userService.createUser(user)).thenReturn(savedUser);
        APIResponse apiResponse = loginService.customerSignUp(customerRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.FOUND.value());
    }

    @Test
    void testDeliveryPersonSignUp_NewCustomerWithSameRole() {
        when(roleService.retrieveRoleByName(any())).thenReturn(deliverPersonRole);
        when(userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(deliveryPersonRequestDto.getEmailId())).thenReturn(deliveryPersonWithSameRole);
        when(userService.createUser(user)).thenReturn(savedUserForDeliveryPerson);
        APIResponse apiResponse = loginService.deliveryPersonSignup(deliveryPersonRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.FOUND.value());
    }

    @Test
    void testUserLoginFailure() {
        when(userService.checkByEmailId(userLoginRequestDto.getEmailId())).thenReturn(false);
        APIResponse apiResponse = loginService.userLogin(userLoginRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    void testUserLoginSuccess() {
        when(userService.checkByEmailId(userLoginRequestDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(userLoginRequestDto.getEmailId())).thenReturn(savedUser);
        APIResponse apiResponse = loginService.userLogin(userLoginRequestDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void testUserLoginSuccessWithToken() {
        when(userService.checkByEmailId(userLoginRequestDtoSuccess.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(userLoginRequestDtoSuccess.getEmailId())).thenReturn(savedUser);
        when(jwtService.generateJwt(savedUser)).thenReturn("JWT Token");
        APIResponse apiResponse = loginService.userLogin(userLoginRequestDtoSuccess);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testVerifyCustomerEmailFailure() {
        APIResponse apiResponse = loginService.verifyCustomerEmail(verifyEmailDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    void testVerifyCustomerEmailSuccess() {
        when(roleService.retrieveRoleByName(any())).thenReturn(role);

        APIResponse apiResponse = loginService.verifyCustomerEmail(verifyEmailDto);
    }

    @Test
    void verifyDeliveryPersonEmailFailure() {
        APIResponse apiResponse = loginService.verifyCustomerEmail(verifyEmailDto);
        assertEquals(apiResponse.getStatus(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    void verifyDeliveryPersonEmailSuccess() {
        when(roleService.retrieveRoleByName(any())).thenReturn(role);
        APIResponse apiResponse = loginService.verifyCustomerEmail(verifyEmailDto);
    }
}