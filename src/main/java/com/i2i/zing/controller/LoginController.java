package com.i2i.zing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.configuration.JwtService;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.dto.UserLoginRequestDto;
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

    @PostMapping("users/login")
    public ResponseEntity<APIResponse> userLogin(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        APIResponse apiResponse = loginService.userLogin(userLoginRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("private/api")
    public ResponseEntity<APIResponse> privateApi() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData("private api");
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
