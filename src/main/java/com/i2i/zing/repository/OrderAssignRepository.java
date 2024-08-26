package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.OrderAssign;

/**
 * <p>
 *    Inserts, deletes, updates and fetches data of the OrderAssign
 *    from the database.
 * </p>
 */
@Repository
public interface OrderAssignRepository extends JpaRepository<OrderAssign, String> {
    /**
     * <p>
     *     Fetches the orderAssigns record by checking their deletion
     *     status using boolean value.
     * </p>
     *
     * @return List<Order> for displaying available orders.
     */
    List<OrderAssign> findByIsDeletedFalse();

    /**
     * <p>
     *     Fetches the Order assigns by checking their deletion status
     *     using boolean value.
     * </p>
     * @param id - String value to fetch the Order assign.
     * @return Order to display the order.
     */
    OrderAssign findByOrderAssignIdAndIsDeletedFalse(String id);
}
