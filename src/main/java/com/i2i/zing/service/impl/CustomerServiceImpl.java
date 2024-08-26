package com.i2i.zing.service.impl;

import com.i2i.zing.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.Membership;
import com.i2i.zing.model.Customer;
import com.i2i.zing.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void createCustomer(Customer customer) {
        customer.setMemberShip(Membership.SILVER);
        customerRepository.save(customer);
    }

}
