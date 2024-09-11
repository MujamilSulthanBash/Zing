package com.i2i.zing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String > {

    /**
     * <p>
     * This method is responsible for fetching customer by userId
     * </p>
     *
     * @param userId - string value for fetching
     * @return - Customer details.
     */
    @Query(value = "FROM Customer c WHERE c.user.id = :userId")
    Customer findByUserId(String userId);

}
