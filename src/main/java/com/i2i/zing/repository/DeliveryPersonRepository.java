package com.i2i.zing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.DeliveryPerson;

/**
 * <p>
 *     This interface has abstract methods for Delivery Person Operations
 * </p>
 */
@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, String> {
    /**
     * <p>
     *     This method retrieve the Delivery Person Object by
     *     Delivery Person ID
     * </p>
     * @param deliveryPersonId - To identify the Delivery Person
     * @return - Delivery Person as Entity Object
     */
    DeliveryPerson findByDeliveryPersonId(String deliveryPersonId);
}