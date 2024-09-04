package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.User;

/**
 * <p>
 * This interface has abstract methods for Delivery Person Operations
 * </p>
 */
@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, String> {

    /**
     * <p>
     * This method is responsible for get the users by their location
     * </p>
     *
     * @param location - Location of the User.
     * @return - List of {@link User} details.
     */
    @Query(value = "FROM DeliveryPerson d LEFT JOIN FETCH User u ON d.user.id = u.id WHERE u.location = :location")
    List<DeliveryPerson> findDeliverPersonByLocation(String location);

}