package com.i2i.zing.service;

import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.model.Order;

/**
 * <p>
 *    Passes value for insertion,deletion and retrieval on Order.
 * </p>
 */
@Service
public interface OrderService {
    /**
     * <p>
     *     Passes the value for insertion into the collection, revokes method to
     *     assign deliveryPerson, revokes stock reduction method to notify the
     *     stock level and revokes mail sender the notify customers.
     * </p>
     *
     * @param orderDto - {@link OrderDto} value to set order Id.
     * @return APIResponse value to indicate insertion status.
     */
    APIResponse addOrder(OrderDto orderDto);

    /**
     * <p>
     *     Fetches all the Orders in application.
     * </p>
     *
     * @return APIResponse value to display Order list.
     */
    APIResponse getOrders();

    /**
     * <p>
     *     Fetches the Order and returns the Order.
     * </p>
     *
     * @param orderId - String value to display the Order.
     * @return OrderDto value to display the Order.
     */
    APIResponse getOrder(String orderId);

    /**
     * <p>
     *     Fetches the Order and returns the Order.
     * </p>
     *
     * @param orderId - String value to display the Order.
     * @return OrderDto value to display the Order.
     */
    Order getOrderById(String orderId);

    /**
     * <p>
     *     Verifies the order by checking the otp, if correct returns updates
     *     corresponding status.
     * </p>
     *
     * @param orderId - String value to check.
     * @return APIResponse value to acknowledge status.
     */
    APIResponse updateOrderStatus(String orderId);
}
