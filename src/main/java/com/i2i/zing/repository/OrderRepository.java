package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.Order;

/**
 * <p>
 * Inserts, deletes, updates and fetches data of the Order.
 * Checks the order along with it's deletion status.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    /**
     * <p>
     * Fetches the orders record by checking their deletion
     * status using boolean value.
     * </p>
     *
     * @return List<Order> for displaying available orders.
     */
    List<Order> findByIsDeletedFalse();

    /**
     * <p>
     * Fetches the Order by checking their deletion status
     * using boolean value.
     * </p>
     *
     * @param id - String value to fetch the Order.
     * @return Order to display the order.
     */
    Order findByOrderIdAndIsDeletedFalse(String id);
}
