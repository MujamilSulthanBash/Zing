package com.i2i.zing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.Membership;
import com.i2i.zing.model.Customer;
import com.i2i.zing.service.CartService;
import com.i2i.zing.service.CustomerService;
import com.i2i.zing.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    CartService cartService;

    public Customer createCustomer(Customer customer) {
        customer.setMemberShip(Membership.SILVER);
        cartService.addCart(customer);
        return customerRepository.save(customer);
    }

    public Customer getCustomer(String customerId) {
        return customerRepository.getReferenceById(customerId);
    }

}
