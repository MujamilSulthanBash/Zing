package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * <p>
 *     This Entity Represents the Cart Details.
 *     It Contains details of cart such as Id, customer' Id and Cart Items Details
 *     It is used to add the Items to the cart by User
 * </p>
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cartId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private Set<CartItem> cartItems;
}
