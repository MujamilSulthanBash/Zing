package com.i2i.zing.service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.OrderAssignDto;
import com.i2i.zing.model.Order;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on OrderAssign.
 * </p>
 */
public interface OrderAssignService {
    /**
     * <p>
     * passes the value for insertion into the collection.
     * </p>
     *
     * @param order - {@link Order} value to set order Id.
     */
    void addOrderAssign(Order order);

    /**
     * <p>
     * Fetches all the OrderAssigns in application.
     * </p>
     *
     * @return APIResponse value to display OrderAssign list.
     */
    APIResponse getOrderAssigns();

    /**
     * <p>
     * Fetches the Order and returns the OrderAssign.
     * </p>
     *
     * @param orderAssignId - String value to display the OrderAssign.
     * @return OrderAssignDto value to display the Order.
     */
    APIResponse getOrderAssign(String orderAssignId);

    /**
     * <p>
     * Updates the delivery status and delivery person of the
     * order assigned.
     * </p>
     *
     * @param orderAssignDto - {@link OrderAssignDto} value to update
     * @return APIResponse value to acknowledge update.
     */
    APIResponse updateOrderAssign(OrderAssignDto orderAssignDto);

    /**
     * <p>
     * Updates the delivery status internally after delivery.
     * </p>
     *
     * @param status  - String value to update.
     * @param OrderId - string value to fetch assigned record.
     * @return APIResponse value to acknowledge update.
     */
    APIResponse updateOrderStatus(String status, String OrderId);

}
