package com.i2i.zing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.CartItem;

/**
 * <p>
 * Inserts, deletes, updates and fetches data of the cart.
 * Checks the cart along with it's deletion status.
 * </p>
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

}
