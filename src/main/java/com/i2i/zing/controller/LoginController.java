package com.i2i.zing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.dto.UserLoginRequestDto;
import com.i2i.zing.dto.VerifyEmailDto;
import com.i2i.zing.service.LoginService;

/**
 * <p>
 * This class is the Controller for Signup and Login Operations
 * like Customer, Delivery Person Signup and Login and
 * also verification of the Email
 * </p>
 */
@RestController
@RequestMapping("zing/api/v1/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * <p>
     * This method is used to sign up the Customer with
     * Customer Details
     * </p>
     *
     * @param customerRequestDto - Customer Details to Signup as Dto object
     * @return - APIResponse (Status Code, Data)
     */
    @PostMapping("customers/signup")
    public ResponseEntity<APIResponse> customerSignUp(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = loginService.customerSignUp(customerRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method is used to sign up the Delivery Person with
     * Delivery Person Details
     * </p>
     *
     * @param deliveryPersonRequestDto - Delivery Person Details for Signup as Dto object
     * @return - APIResponse (Status COde, Data)
     */
    @PostMapping("deliverypersons/signup")
    public ResponseEntity<APIResponse> deliveryPersonSignup(@Valid @RequestBody DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = loginService.deliveryPersonSignup(deliveryPersonRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method is used to authenticate the User login Credentials
     * </p>
     *
     * @param userLoginRequestDto - Login Request Details like Username, Password
     * @return - APIResponse (Status Code, Data)
     */
    @PostMapping("users/login")
    public ResponseEntity<APIResponse> userLogin(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        APIResponse apiResponse = loginService.userLogin(userLoginRequestDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * This method is used to authenticate the User login Credentials
     * </p>
     *
     * @param verifyEmailDto - Login Request Details like User email, otp
     * @return - APIResponse (Status Code, Data)
     */
    @PostMapping("customers/verify")
    public ResponseEntity<APIResponse> verifyCustomerMail(@Valid @RequestBody VerifyEmailDto verifyEmailDto) {
        APIResponse apiResponse = loginService.verifyCustomerEmail(verifyEmailDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     *     This method verify the Delivery Person Email
     *     with OTP Verification
     * </p>
     * @param verifyEmailDto {@link VerifyEmailDto} - Email Id and Password
     * @return APIResponse (Status, Data)
     */
    @PostMapping("deliverypersons/verify")
    public ResponseEntity<APIResponse> verifyDeliveryPersonsMail(@Valid @RequestBody VerifyEmailDto verifyEmailDto) {
        APIResponse apiResponse = loginService.verifyDeliveryPersonEmail(verifyEmailDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
