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

    private Double quantity;

    private Double price;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cart cart;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;
}
