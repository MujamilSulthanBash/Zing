package com.i2i.zing.controller;

import com.i2i.zing.dto.CustomerLoginRequestDto;
import com.i2i.zing.dto.DeliveryPersonLoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.service.LoginService;

@RestController
@RequestMapping("zing/api/v1/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("customers/signup")
    public ResponseEntity<APIResponse> customerSignUp(@RequestBody CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = loginService.customerSignUp(customerRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("deliverypersons/signup")
    public ResponseEntity<APIResponse> deliveryPersonSignup(@RequestBody DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = loginService.deliveryPersonSignup(deliveryPersonRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);

    }

    @PostMapping("customers/login")
    public ResponseEntity<APIResponse> customerLogin(@RequestBody CustomerLoginRequestDto customerLoginRequestDto) {
        APIResponse apiResponse = loginService.customerLogin(customerLoginRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("deliverypersons/login")
    public ResponseEntity<APIResponse> deliveryPersonLogin(@RequestBody DeliveryPersonLoginRequestDto deliveryPersonLoginRequestDto) {
        APIResponse apiResponse = loginService.deliveryPersonLogin(deliveryPersonLoginRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);

    }

}
