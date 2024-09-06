package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.OrderAssign;

/**
 * <p>
 * Inserts, deletes, updates and fetches data of the OrderAssign
 * from the database.
 * </p>
 */
@Repository
public interface OrderAssignRepository extends JpaRepository<OrderAssign, String> {
    /**
     * <p>
     * Fetches the orderAssigns record by checking their deletion
     * status using boolean value.
     * </p>
     *
     * @return List<OrderAssign> for displaying available orderAssigns.
     */
    List<OrderAssign> findByIsDeletedFalse();

    /**
     * <p>
     * Fetches the Order assigns by checking their deletion status
     * using boolean value.
     * </p>
     *
     * @param id - String value to fetch the Order assign.
     * @return OrderAssign to display the orderAssign.
     */
    OrderAssign findByAssignIdAndIsDeletedFalse(String id);

    /**
     * <p>
     * Fetches the Order assigns by checking their related
     * order using query.
     * </p>
     *
     * @param orderId - String value to fetch the Order assign.
     * @return OrderAssign to display the orderAssign.
     */
    @Query(value = "FROM OrderAssign a RIGHT JOIN FETCH Order o ON a.order.id = :orderId")
    OrderAssign findByOrderId(String orderId);

}
