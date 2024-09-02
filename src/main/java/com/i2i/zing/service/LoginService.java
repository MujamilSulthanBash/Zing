package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.dto.UserLoginRequestDto;
import com.i2i.zing.dto.VerifyEmailDto;

/**
 * <p>
 *     This interface has abstract methods for
 *     Login Operations
 * </p>
 */
public interface LoginService {
    /**
     * <p>
     * This method is used to Sign up for Customer
     * </p>
     *
     * @param customerRequestDto - {@link CustomerRequestDto} Details as Dto Object
     * @return - APIResponse (Status Code, Data, Error)
     */
    APIResponse customerSignUp(CustomerRequestDto customerRequestDto);

    /**
     * <p>
     * This method is used to Sign up the Delivery Person
     * </p>
     *
     * @param deliveryPersonRequestDto - {@link DeliveryPersonRequestDto} - Delivery Persons Credentials for Sign Up
     * @return - APIResponse (Status Code, Data, Error)
     */
    APIResponse deliveryPersonSignup(DeliveryPersonRequestDto deliveryPersonRequestDto);

    /**
     * <p>
     * This method is used to Customer Login
     * </p>
     *
     * @param userLoginRequestDto - {@link UserLoginRequestDto} Customer Credentials for Login
     * @return - APIResponse (Status Code, Data, Error)
     */
    APIResponse userLogin(UserLoginRequestDto userLoginRequestDto);

    /**
     * <p>
     *     This method verify the Customer Email Id
     *     valid or not
     * </p>
     * @param verifyEmailDto {@link VerifyEmailDto} - Details like Email ID and Password
     * @return APIResponse (Status Code, Data, Error)
     */
    APIResponse verifyCustomerEmail(VerifyEmailDto verifyEmailDto);

    /**
     * <p>
     *     This method verify the Delivery Person Email Id
     *     Valid or not
     * </p>
     * @param verifyEmailDto {@link VerifyEmailDto} - Details like Email and Password
     * @return APIResponse (Status Code, Data, Error)
     */
    APIResponse verifyDeliveryPersonEmail(VerifyEmailDto verifyEmailDto);

}
