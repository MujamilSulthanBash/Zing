package com.i2i.zing.service;

import com.i2i.zing.model.Order;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on OrderAssign.
 * </p>
 */
@Service
public interface OrderAssignService {
    /**
     * <p>
     *     passes the value for insertion into the collection.
     * </p>
     *
     * @param order - {@link Order} value to set order Id.
     */
    void addOrderAssign(Order order);

    /**
     * <p>
     *     Fetches all the OrderAssigns in application.
     * </p>
     *
     * @return APIResponse value to display OrderAssign list.
     */
    APIResponse getOrderAssigns();

    /**
     * <p>
     *     Fetches the Order and returns the OrderAssign.
     * </p>
     *
     * @param orderAssignId - String value to display the OrderAssign.
     * @return OrderAssignDto value to display the Order.
     */
    APIResponse getOrderAssign(String orderAssignId);

    /**
     * <p>
     *     Removes the OrderAssign from user view by changing boolean value.
     * </p>
     * @param orderAssignId - String to remove OrderAssign.
     * @return APIResponse value to acknowledge deletion.
     */
    APIResponse deleteOrderAssign(String orderAssignId);
}
