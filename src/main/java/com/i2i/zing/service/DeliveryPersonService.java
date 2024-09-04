package com.i2i.zing.service;

import java.util.List;

import com.i2i.zing.model.DeliveryPerson;

/**
 * <p>
 * This interface has abstract methods for
 * Delivery Person Operations
 * </p>
 */
public interface DeliveryPersonService {
    /**
     * <p>
     * This method create Delivery Person in the Database
     * </p>
     *
     * @param deliveryPerson - Delivery Person as Entity Object
     */
    void createDeliveryPerson(DeliveryPerson deliveryPerson);

    /**
     * <p>
     * This method return the Delivery Person by the
     * Delivery Person ID
     * </p>
     *
     * @param location - To Identify the Delivery person location
     * @return - Delivery Person as Entity Object
     */
    List<DeliveryPerson> getDeliveryPersonByLocation(String location);

}
