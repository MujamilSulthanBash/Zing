package com.i2i.zing.service;

import com.i2i.zing.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on Order.
 * </p>
 */
@Service
public interface OrderService {
    /**
     * <p>
     *     passes the value for insertion into the collection.
     * </p>
     *
     * @param orderDto - {@link OrderDto} value to set order Id.
     * @return OrderDto value to indicate insertion status.
     */
    OrderDto addOrder(OrderDto orderDto);

    /**
     * <p>
     *     Fetches all the Orders in application.
     * </p>
     *
     * @return List<OrderDto> value to display Order list.
     */
    List<OrderDto> getOrders();

    /**
     * <p>
     *     Fetches the Order and returns the Order.
     * </p>
     *
     * @param orderId - String value to display the Order.
     * @return OrderDto value to display the Order.
     */
    OrderDto getOrder(String orderId);

    /**
     * <p>
     *     Removes the Order from user view by changing boolean value.
     * </p>
     * @param orderId - String to remove Order.
     */
    void deleteOrder(String orderId);
}
