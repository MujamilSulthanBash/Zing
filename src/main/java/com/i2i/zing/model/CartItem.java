package com.i2i.zing.model;

import com.i2i.zing.common.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

/**
 * <p>
 *     This Entity Represents the Details of
 *     Items in the Cart such as Item ID, Quantity
 *     Total prize of the items and payment Status
 * </p>
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int quantity;

    private Double price;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Cart cart;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "item_id")
    private Item item;
}
