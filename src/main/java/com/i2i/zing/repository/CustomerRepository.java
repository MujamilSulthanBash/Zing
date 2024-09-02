package com.i2i.zing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String > {

}
