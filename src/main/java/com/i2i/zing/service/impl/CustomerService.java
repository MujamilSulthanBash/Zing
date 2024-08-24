package com.i2i.zing.service.impl;

import com.i2i.zing.common.Membership;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void createCustomer(User user) {
        Customer customer = Customer.builder()
                .user(user)
                .memberShip(Membership.SILVER)
                .build();
        customerRepository.save(customer);
    }



}
