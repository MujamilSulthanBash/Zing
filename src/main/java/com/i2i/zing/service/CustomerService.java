package com.i2i.zing.service;

import com.i2i.zing.model.Customer;

public interface CustomerService {

    /**
     * <p>
     * This method create the customer after signup
     * </p>
     *
     * @param customer - as Entity Object
     */
    void createCustomer(Customer customer);

    /**
     * <p>
     * This method get the Customer with Customer Id
     * </p>
     *
     * @param customerId - To Identify the Customer
     * @return Customer as Entity Object
     */
    Customer getCustomer(String customerId);

}
