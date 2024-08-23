package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.Cart;

/**
 * <p>
 *    Inserts, deletes, updates and fetches data of the cart.
 *    Checks the cart along with it's deletion status.
 * </p>
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
        /**
         * <p>
         *     Fetches the carts record by checking their deletion
         *     status using boolean value.
         * </p>
         *
         * @return List<Cart> for displaying available carts.
         */
        List<Cart> findByIsDeletedFalse();

        /**
         * <p>
         *     Fetches the Cart by checking their deletion status
         *     using boolean value.
         * </p>
         * @param id - String value to fetch the Cart.
         * @param isDeleted - boolean value to check deletion status.
         * @return Cart to display the cart.
         */
        Cart findByCartIdAndIsDeleted(String id, boolean isDeleted);
}
