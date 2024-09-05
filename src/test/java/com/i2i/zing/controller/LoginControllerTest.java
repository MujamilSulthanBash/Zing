package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.dto.UserLoginRequestDto;
import com.i2i.zing.dto.VerifyEmailDto;
import com.i2i.zing.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    @Mock
    LoginServiceImpl loginService;

    @InjectMocks
    LoginController loginController;

    CustomerRequestDto customerRequestDto;

    DeliveryPersonRequestDto deliveryPersonRequestDto;

    UserLoginRequestDto userLoginRequestDto;

    VerifyEmailDto verifyEmailDto;

    APIResponse signUpResponse;

    APIResponse deliveryPersonResponse;

    APIResponse userLoginResponse;

    APIResponse verifyEmailResponse;

    @BeforeEach
    void setUp() {
        customerRequestDto = CustomerRequestDto.builder()
                .userName("Aravind")
                .password("1234567")
                .contactNumber("9843264403")
                .emailId("apsaravindvimal@gmail.com")
                .location("Guindy")
                .build();
        deliveryPersonRequestDto = DeliveryPersonRequestDto.builder()
                .userName("Sai")
                .password("saiprasath")
                .location("Guindy")
                .emailId("sai@gmail.com")
                .aadharNumber("224466778899")
                .contactNumber("9876543210")
                .vehicleNumber("TN:67 1234")
                .licenseNumber("12345")
                .build();
        userLoginRequestDto = UserLoginRequestDto.builder()
                .emailId("apsaravindvimal@gmail.com")
                .password("information")
                .build();
        verifyEmailDto = VerifyEmailDto.builder()
                .email("apsaravindvimal@gmail.com")
                .otp("4622")
                .build();
        signUpResponse = APIResponse.builder()
                .data(customerRequestDto)
                .status(HttpStatus.CREATED.value())
                .build();
        deliveryPersonResponse = APIResponse.builder()
                .data(deliveryPersonRequestDto)
                .status(HttpStatus.CREATED.value())
                .build();
        userLoginResponse = APIResponse.builder()
                .data(userLoginRequestDto)
                .status(HttpStatus.OK.value())
                .build();
        verifyEmailResponse = APIResponse.builder()
                .data(verifyEmailDto)
                .status(HttpStatus.OK.value())
                .build();
    }

    @Test
    public void testSignUp() {
        when(loginService.customerSignUp(customerRequestDto)).thenReturn(signUpResponse);
        ResponseEntity<APIResponse> signUpResponse = loginController.customerSignUp(customerRequestDto);
        assertEquals(HttpStatus.CREATED, signUpResponse.getStatusCode());
    }

    @Test
    public void testDeliveryPersonSignUp() {
        when(loginService.deliveryPersonSignup(deliveryPersonRequestDto)).thenReturn(deliveryPersonResponse);
        ResponseEntity<APIResponse> deliveryPersonResponse = loginController.deliveryPersonSignup(deliveryPersonRequestDto);
        assertEquals(HttpStatus.CREATED, deliveryPersonResponse.getStatusCode());
    }

    @Test
    public void testUserLogin() {
        when(loginService.userLogin(userLoginRequestDto)).thenReturn(userLoginResponse);
        ResponseEntity<APIResponse> userLoginResponse = loginController.userLogin(userLoginRequestDto);
        assertEquals(HttpStatus.OK, userLoginResponse.getStatusCode());
    }

    @Test
    public void testVerifyCustomerMail() {
        when(loginService.verifyCustomerEmail(verifyEmailDto)).thenReturn(verifyEmailResponse);
        ResponseEntity<APIResponse> verifyEmailResponse = loginController.verifyCustomerMail(verifyEmailDto);
        assertEquals(HttpStatus.OK, verifyEmailResponse.getStatusCode());
    }

    @Test
    public void testVerifyDeliveryPersonMail() {
        when(loginService.verifyDeliveryPersonEmail(verifyEmailDto)).thenReturn(verifyEmailResponse);
        ResponseEntity<APIResponse> verifyEmailResponse = loginController.verifyDeliveryPersonsMail(verifyEmailDto);
        assertEquals(HttpStatus.OK, verifyEmailResponse.getStatusCode());
    }
}
