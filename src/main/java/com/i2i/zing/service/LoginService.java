package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CustomerLoginRequestDto;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonLoginRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;

/**
 * <p>
 *     This interface has abstract methods for
 *     Login Operations
 * </p>
 */
public interface LoginService {
    /**
     * <p>
     *     This method is used to Sign up for Customer
     * </p>
     * @param customerRequestDto - {@link CustomerRequestDto} Details as Dto Object
     * @return - APIResponse (Status Code, Data, Error)
     */
    APIResponse customerSignUp(CustomerRequestDto customerRequestDto);

    /**
     * <p>
     *     This method is used to Customer Login
     * </p>
     * @param customerLoginRequestDto - {@link CustomerLoginRequestDto} Customer Credentials for Login
     * @return - APIResponse (Status Code, Data, Error)
     */
    APIResponse customerLogin(CustomerLoginRequestDto customerLoginRequestDto);

    /**
     * <p>
     *     This method is used to Sign up the Delivery Person
     * </p>
     * @param deliveryPersonRequestDto - {@link DeliveryPersonRequestDto} - Delivery Persons Credentials for Sign Up
     * @return - APIResponse (Status Code, Data, Error)
     */
    APIResponse deliveryPersonSignup(DeliveryPersonRequestDto deliveryPersonRequestDto);

    /**
     * <p>
     *     This method is used for Delivery Person Login
     * </p>
     * @param deliveryPersonLoginRequestDto - {@link DeliveryPersonLoginRequestDto} - Delivery Person Credentials for Login
     * @return - APIResponse(Status Code, Data, Error)
     */
    APIResponse deliveryPersonLogin(DeliveryPersonLoginRequestDto deliveryPersonLoginRequestDto);
}
