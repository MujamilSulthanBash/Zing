package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.VerifyOrderDto;
import com.i2i.zing.model.DeliveryPerson;

/**
 * <p>
 *     This interface has abstract methods for
 *     Delivery Person Operations
 * </p>
 */
public interface DeliveryPersonService {
    /**
     * <p>
     *     This method create Delivery Person in the Database
     * </p>
     * @param deliveryPerson - Delivery Person as Entity Object
     */
    void createDeliveryPerson(DeliveryPerson deliveryPerson);

    /**
     * <p>
     *     This method return the Delivery Person by the
     *     Delivery Person ID
     * </p>
     * @param userId - To Identify the User
     * @return - Delivery Person as Entity Object
     */
    DeliveryPerson getDeliveryPersonById(String userId);

}
