package com.i2i.zing.service;

import java.util.List;

import com.i2i.zing.common.APIResponse;
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
     * @return DeliveryPerson - {@link DeliveryPerson} details.
     */
    DeliveryPerson createDeliveryPerson(DeliveryPerson deliveryPerson);

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

    /**
     * <p>
     * Fetches the deliveryPerson and returns the all the orders assigned to him.
     * </p>
     *
     * @param deliveryPersonId - String value to display the assigned order details.
     * @return APIResponse value to display the Cart.
     */
    APIResponse getOrderAssignsOfDeliveryPerson(String deliveryPersonId);

}
