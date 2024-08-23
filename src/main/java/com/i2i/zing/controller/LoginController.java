package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("zing/api/v1/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("customer/signup")
    public ResponseEntity<APIResponse> signUp(@RequestBody CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = loginService.signUp(customerRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("deliveryperson/signup")
    public ResponseEntity<APIResponse> signUpDeliveryPerson(@RequestBody DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = loginService.signUpDeliveryPerson(deliveryPersonRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);

    }

}
