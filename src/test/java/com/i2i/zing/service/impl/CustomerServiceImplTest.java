package com.i2i.zing.service.impl;

import com.i2i.zing.mapper.UserMapperTest;
import com.i2i.zing.model.Customer;
import com.i2i.zing.repository.CustomerRepository;
import com.i2i.zing.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    CartService cartService;

    @InjectMocks
    CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = UserMapperTest.getCustomer();
    }

    @Test
    void createCustomerSuccess() {
        when(customerRepository.save(customer)).thenReturn(customer);
        cartService.addCart(customer);
        customerService.createCustomer(customer);
    }

    @Test
    void getCustomer() {
        when(customerRepository.getReferenceById(customer.getCustomerId())).thenReturn(customer);
        assertEquals(customerService.getCustomer(customer.getCustomerId()).getCustomerId(), customer.getCustomerId());
    }
}