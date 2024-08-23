package com.i2i.zing.model;

import com.i2i.zing.common.PaymentMethod;
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
@Setter
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cartId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "payment_Method")
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
